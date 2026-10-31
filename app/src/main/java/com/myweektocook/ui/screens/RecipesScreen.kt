package com.myweektocook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.myweektocook.data.ProteinType
import com.myweektocook.data.RecipeEntity
import com.myweektocook.ui.MealPlanUiState
import com.myweektocook.ui.MealPlanViewModel
import com.myweektocook.ui.components.RecipeEditorDialog

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipesScreen(
    uiState: MealPlanUiState,
    viewModel: MealPlanViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedProtein by remember { mutableStateOf<ProteinType?>(null) }
    var editingRecipe by remember { mutableStateOf<RecipeEntity?>(null) }
    var showAddRecipeDialog by remember { mutableStateOf(false) }

    val filtered = uiState.recipes.filter { recipe ->
        val matchesQuery = searchQuery.isBlank() ||
            recipe.title.contains(searchQuery, ignoreCase = true) ||
            recipe.suggestedSides.any { it.contains(searchQuery, ignoreCase = true) }
        val matchesProtein = selectedProtein == null || recipe.protein == selectedProtein
        matchesQuery && matchesProtein
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Recipe Collection (${uiState.recipes.size})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Manage main courses, cook times, proteins & suggested sides",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Button(onClick = { showAddRecipeDialog = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("New Recipe")
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search recipes or sides...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ProteinType.entries.forEach { protein ->
                            FilterChip(
                                selected = selectedProtein == protein,
                                onClick = {
                                    selectedProtein = if (selectedProtein == protein) null else protein
                                },
                                label = { Text(protein.displayName) }
                            )
                        }
                    }
                }
            }
        }

        items(filtered, key = { it.id }) { recipe ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (recipe.isTrial) {
                                    Surface(
                                        color = MaterialTheme.colorScheme.tertiary,
                                        contentColor = Color.White,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "TRIAL",
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "${recipe.protein.displayName} • ${recipe.prepAndCookMinutes} min • ${recipe.ingredients.size} ingredients",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Row {
                            IconButton(onClick = { editingRecipe = recipe }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Recipe")
                            }
                            IconButton(onClick = { viewModel.discardRecipe(recipe) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Recipe",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }

                    if (recipe.suggestedSides.isNotEmpty()) {
                        Text(
                            text = "Suggested Sides: ${recipe.suggestedSides.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (recipe.isTrial) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { viewModel.promoteTrialRecipeToPermanent(recipe) }) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Permanently to Collection")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddRecipeDialog) {
        RecipeEditorDialog(
            initialRecipe = null,
            defaultIsTrial = true,
            onDismiss = { showAddRecipeDialog = false },
            onSave = { id, title, protein, minutes, ingredients, instructions, sides, isTrial ->
                viewModel.saveRecipe(
                    id = id,
                    title = title,
                    protein = protein,
                    prepAndCookMinutes = minutes,
                    ingredients = ingredients,
                    instructions = instructions,
                    suggestedSides = sides,
                    isTrial = isTrial
                )
                showAddRecipeDialog = false
            }
        )
    }

    editingRecipe?.let { recipe ->
        RecipeEditorDialog(
            initialRecipe = recipe,
            defaultIsTrial = recipe.isTrial,
            onDismiss = { editingRecipe = null },
            onSave = { id, title, protein, minutes, ingredients, instructions, sides, isTrial ->
                viewModel.saveRecipe(
                    id = id,
                    title = title,
                    protein = protein,
                    prepAndCookMinutes = minutes,
                    ingredients = ingredients,
                    instructions = instructions,
                    suggestedSides = sides,
                    isTrial = isTrial
                )
                editingRecipe = null
            }
        )
    }
}
