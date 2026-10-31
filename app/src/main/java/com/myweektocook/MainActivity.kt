package com.myweektocook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myweektocook.ui.MealPlanViewModel
import com.myweektocook.ui.screens.CookingScreen
import com.myweektocook.ui.screens.PlanScreen
import com.myweektocook.ui.screens.RecipesScreen
import com.myweektocook.ui.screens.ShoppingScreen
import com.myweektocook.ui.theme.MyWeekToCookTheme

enum class AppPhaseTab(val label: String, val icon: ImageVector) {
    PLAN("1. Plan", Icons.Default.CalendarMonth),
    SHOP("2. Shop", Icons.Default.ShoppingCart),
    COOK("3. Cook", Icons.Default.Restaurant),
    RECIPES("Recipes", Icons.Default.MenuBook)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeekToCookTheme {
                MyWeekToCookMainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWeekToCookMainScreen(
    viewModel: MealPlanViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var currentTab by rememberSaveable { mutableStateOf(AppPhaseTab.PLAN) }

    val uncheckedShoppingCount = uiState.shoppingItems.count { !it.isChecked }
    val plannedDinnersCount = uiState.plannedDinners.count { it.recipe != null }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Week to Cook",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                AppPhaseTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        icon = {
                            BadgedBox(
                                badge = {
                                    when (tab) {
                                        AppPhaseTab.PLAN -> if (plannedDinnersCount > 0) {
                                            Badge { Text("$plannedDinnersCount") }
                                        }
                                        AppPhaseTab.SHOP -> if (uncheckedShoppingCount > 0) {
                                            Badge { Text("$uncheckedShoppingCount") }
                                        }
                                        else -> {}
                                    }
                                }
                            ) {
                                Icon(imageVector = tab.icon, contentDescription = tab.label)
                            }
                        },
                        label = {
                            Text(
                                text = tab.label,
                                fontWeight = if (currentTab == tab) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppPhaseTab.PLAN -> PlanScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigateToShopping = { currentTab = AppPhaseTab.SHOP },
                    onNavigateToCooking = { slotIndex ->
                        viewModel.selectCookingSlot(slotIndex)
                        currentTab = AppPhaseTab.COOK
                    }
                )
                AppPhaseTab.SHOP -> ShoppingScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
                AppPhaseTab.COOK -> CookingScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    onNavigateToPlan = { currentTab = AppPhaseTab.PLAN }
                )
                AppPhaseTab.RECIPES -> RecipesScreen(
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    }
}
