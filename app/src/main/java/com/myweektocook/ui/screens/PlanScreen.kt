package com.myweektocook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import com.myweektocook.ui.PlannedDinnerUiModel
import com.myweektocook.ui.components.RecipeEditorDialog

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanScreen(
    uiState: MealPlanUiState,
    viewModel: MealPlanViewModel,
    onNavigateToShopping: () -> Unit,
    onNavigateToCooking: (Int) -> Unit
) {
    var activePickerSlot by remember { mutableStateOf<PlannedDinnerUiModel?>(null) }
    var addTrialForSlotIndex by remember { mutableStateOf<Int?>(null) }
    var addCustomSideForSlot by remember { mutableStateOf<Int?>(null) }
    var editDayLabelForSlot by remember { mutableStateOf<PlannedDinnerUiModel?>(null) }
    var showResetDialog by remember { mutableStateOf(false) }

    val plannedCount = uiState.plannedDinners.count { it.recipe != null }
    val totalSlots = uiState.plannedDinners.size
    val dinnersMissingSides = uiState.plannedDinners.count { it.recipe != null && it.slot.selectedSides.isEmpty() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // --- Header & Bi-Weekly Plan Overview Card ---
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Dinner Plan for the Week",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$plannedCount of $totalSlots dinners selected",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { showResetDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Start New Week"
                            )
                        }
                    }

                    // Step 1: How many dinners this week? (Usually 3 to 5)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Dinners this week:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(3, 4, 5, 6).forEach { count ->
                                FilterChip(
                                    selected = totalSlots == count,
                                    onClick = { viewModel.setTargetDinnerCount(count) },
                                    label = { Text("$count") }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // Protein Variety Check ("Try not to use the same protein more than once... twice at most")
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Protein Variety Check (Max 1–2 per protein):",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (uiState.proteinCounts.isEmpty()) {
                            Text(
                                text = "Pick main courses below to check protein balance.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                uiState.proteinCounts.entries.sortedByDescending { it.value }.forEach { (protein, count) ->
                                    val isOverLimit = count > 2
                                    val isAtMax = count == 2
                                    val containerColor = when {
                                        isOverLimit -> MaterialTheme.colorScheme.errorContainer
                                        isAtMax -> Color(0xFFFEF3C7)
                                        else -> MaterialTheme.colorScheme.secondaryContainer
                                    }
                                    val contentColor = when {
                                        isOverLimit -> MaterialTheme.colorScheme.onErrorContainer
                                        isAtMax -> Color(0xFF92400E)
                                        else -> MaterialTheme.colorScheme.onSecondaryContainer
                                    }
                                    Surface(
                                        color = containerColor,
                                        contentColor = contentColor,
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            if (isOverLimit) {
                                                Icon(
                                                    imageVector = Icons.Default.Warning,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                            Text(
                                                text = "${protein.displayName}: ${count}x",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }
                            if (uiState.proteinCounts.values.any { it > 2 }) {
                                Text(
                                    text = "Note: You have the same protein more than twice this week.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Sides Review Status
                    if (plannedCount > 0) {
                        val allHaveSides = dinnersMissingSides == 0
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (allHaveSides) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (allHaveSides) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (allHaveSides) {
                                    "All planned dinners have 1–2 sides selected!"
                                } else {
                                    "$dinnersMissingSides planned dinner(s) still need sides selected."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Push to Shopping List CTA
                    Button(
                        onClick = {
                            viewModel.generateShoppingListFromPlan {
                                onNavigateToShopping()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = plannedCount > 0
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Send Ingredients + Staples to Shopping List")
                    }
                }
            }
        }

        // --- Planned Dinner Cards ---
        items(uiState.plannedDinners, key = { it.slot.slotIndex }) { dinnerModel ->
            DinnerSlotCard(
                dinnerModel = dinnerModel,
                onToggleTimeLimited = { viewModel.toggleTimeLimited(dinnerModel.slot.slotIndex) },
                onEditDayLabel = { editDayLabelForSlot = dinnerModel },
                onPickRecipe = { activePickerSlot = dinnerModel },
                onClearRecipe = { viewModel.assignRecipeToSlot(dinnerModel.slot.slotIndex, null) },
                onToggleSide = { side -> viewModel.toggleSideForDinner(dinnerModel.slot.slotIndex, side) },
                onAddCustomSide = { addCustomSideForSlot = dinnerModel.slot.slotIndex },
                onStartCooking = { onNavigateToCooking(dinnerModel.slot.slotIndex) }
            )
        }
    }

    // --- Recipe Picker Modal ---
    activePickerSlot?.let { slotModel ->
        RecipePickerDialog(
            slotModel = slotModel,
            recipes = uiState.recipes,
            proteinCounts = uiState.proteinCounts,
            onDismiss = { activePickerSlot = null },
            onSelectRecipe = { recipe ->
                viewModel.assignRecipeToSlot(slotModel.slot.slotIndex, recipe)
                activePickerSlot = null
            },
            onCreateNewTrialRecipe = {
                val targetSlot = slotModel.slot.slotIndex
                activePickerSlot = null
                addTrialForSlotIndex = targetSlot
            }
        )
    }

    // --- Add New Trial Recipe Dialog ---
    addTrialForSlotIndex?.let { slotIndex ->
        RecipeEditorDialog(
            initialRecipe = null,
            defaultIsTrial = true,
            onDismiss = { addTrialForSlotIndex = null },
            onSave = { id, title, protein, minutes, ingredients, instructions, sides, isTrial ->
                viewModel.saveRecipe(
                    id = id,
                    title = title,
                    protein = protein,
                    prepAndCookMinutes = minutes,
                    ingredients = ingredients,
                    instructions = instructions,
                    suggestedSides = sides,
                    isTrial = isTrial,
                    assignToSlotIndex = slotIndex
                )
                addTrialForSlotIndex = null
            }
        )
    }

    // --- Add Custom Lightweight Side Dialog ---
    addCustomSideForSlot?.let { slotIndex ->
        var sideText by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { addCustomSideForSlot = null },
            title = { Text("Add Side for Dinner ${slotIndex + 1}") },
            text = {
                OutlinedTextField(
                    value = sideText,
                    onValueChange = { sideText = it },
                    label = { Text("Side Dish Name") },
                    placeholder = { Text("e.g. Side Salad, Roasted Broccoli") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    enabled = sideText.isNotBlank(),
                    onClick = {
                        viewModel.toggleSideForDinner(slotIndex, sideText)
                        addCustomSideForSlot = null
                    }
                ) {
                    Text("Add Side")
                }
            },
            dismissButton = {
                TextButton(onClick = { addCustomSideForSlot = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- Rename Dinner Slot / Day Label Dialog ---
    editDayLabelForSlot?.let { slotModel ->
        var dayName by remember { mutableStateOf(slotModel.slot.dayLabel) }
        AlertDialog(
            onDismissRequest = { editDayLabelForSlot = null },
            title = { Text("Label Dinner Slot") },
            text = {
                OutlinedTextField(
                    value = dayName,
                    onValueChange = { dayName = it },
                    label = { Text("Day or Dinner Name") },
                    placeholder = { Text("e.g. Monday, Busy Wednesday, Dinner 1") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updateDayLabel(slotModel.slot.slotIndex, dayName)
                        editDayLabelForSlot = null
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editDayLabelForSlot = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- Start New Week Dialog ---
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Start New Week to Cook?") },
            text = {
                Text("This clears the current week's dinner selections so you can plan your next week.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.startNewWeek(clearShoppingList = true)
                        showResetDialog = false
                    }
                ) {
                    Text("Reset Plan & Shopping List")
                }
            },
            dismissButton = {
                Row {
                    TextButton(
                        onClick = {
                            viewModel.startNewWeek(clearShoppingList = false)
                            showResetDialog = false
                        }
                    ) {
                        Text("Reset Plan Only")
                    }
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DinnerSlotCard(
    dinnerModel: PlannedDinnerUiModel,
    onToggleTimeLimited: () -> Unit,
    onEditDayLabel: () -> Unit,
    onPickRecipe: () -> Unit,
    onClearRecipe: () -> Unit,
    onToggleSide: (String) -> Unit,
    onAddCustomSide: () -> Unit,
    onStartCooking: () -> Unit
) {
    val slot = dinnerModel.slot
    val recipe = dinnerModel.recipe

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Top bar: Day label + Limited Cook Time toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onEditDayLabel() }
                ) {
                    Text(
                        text = slot.dayLabel,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Day Label",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                FilterChip(
                    selected = slot.isTimeLimited,
                    onClick = onToggleTimeLimited,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = {
                        Text(
                            text = if (slot.isTimeLimited) "Limited Time (≤35m)" else "Normal Time",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
            }

            if (recipe == null) {
                OutlinedButton(
                    onClick = onPickRecipe,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.RestaurantMenu,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (slot.isTimeLimited) "Pick Quick Main Course (≤35 min)" else "Pick Main Course Recipe"
                    )
                }
            } else {
                // Selected Recipe summary box
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = recipe.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = recipe.protein.displayName,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "• ${recipe.prepAndCookMinutes} min",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (slot.isTimeLimited && recipe.prepAndCookMinutes > 35) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                    if (recipe.isTrial) {
                                        Surface(
                                            color = MaterialTheme.colorScheme.tertiary,
                                            contentColor = Color.White,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "TRIAL RECIPE",
                                                style = MaterialTheme.typography.labelSmall,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                            Row {
                                TextButton(onClick = onPickRecipe) {
                                    Text("Swap")
                                }
                                IconButton(onClick = onClearRecipe) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear Recipe"
                                    )
                                }
                            }
                        }

                        if (slot.isTimeLimited && recipe.prepAndCookMinutes > 35) {
                            Text(
                                text = "Heads up: This recipe takes ${recipe.prepAndCookMinutes}m on a time-limited evening.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                // Sides Selection Section ("make sure each also includes one or two sides")
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sides (${slot.selectedSides.size} selected — pick 1 or 2):",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = if (slot.selectedSides.isEmpty()) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        TextButton(onClick = onAddCustomSide) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Custom Side")
                        }
                    }

                    val allAvailableSides = (recipe.suggestedSides + slot.selectedSides)
                        .distinctBy { it.lowercase() }

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        allAvailableSides.forEach { side ->
                            val isSelected = slot.selectedSides.any { it.equals(side, ignoreCase = true) }
                            FilterChip(
                                selected = isSelected,
                                onClick = { onToggleSide(side) },
                                label = { Text(side) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    }
                }

                // Quick link to Cook view
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onStartCooking) {
                        Text("Open in Cooking View →", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipePickerDialog(
    slotModel: PlannedDinnerUiModel,
    recipes: List<RecipeEntity>,
    proteinCounts: Map<ProteinType, Int>,
    onDismiss: () -> Unit,
    onSelectRecipe: (RecipeEntity) -> Unit,
    onCreateNewTrialRecipe: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var filterQuickOnly by remember { mutableStateOf(slotModel.slot.isTimeLimited) }
    var selectedProteinFilter by remember { mutableStateOf<ProteinType?>(null) }

    val filteredRecipes = recipes.filter { recipe ->
        val matchesQuery = searchQuery.isBlank() ||
            recipe.title.contains(searchQuery, ignoreCase = true) ||
            recipe.protein.displayName.contains(searchQuery, ignoreCase = true)
        val matchesQuick = !filterQuickOnly || recipe.prepAndCookMinutes <= 35
        val matchesProtein = selectedProteinFilter == null || recipe.protein == selectedProteinFilter
        matchesQuery && matchesQuick && matchesProtein
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Select Main Course for ${slotModel.slot.dayLabel}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (slotModel.slot.isTimeLimited) {
                    Text(
                        text = "Time-limited evening: filtering for quick recipes (≤35m)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onCreateNewTrialRecipe,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Research / Add New Trial Recipe")
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search recipes or proteins...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FilterChip(
                        selected = filterQuickOnly,
                        onClick = { filterQuickOnly = !filterQuickOnly },
                        label = { Text("≤ 35 min") }
                    )
                    ProteinType.entries.forEach { protein ->
                        FilterChip(
                            selected = selectedProteinFilter == protein,
                            onClick = {
                                selectedProteinFilter = if (selectedProteinFilter == protein) null else protein
                            },
                            label = {
                                val usedCount = proteinCounts[protein] ?: 0
                                Text(if (usedCount > 0) "${protein.displayName} (${usedCount}x)" else protein.displayName)
                            }
                        )
                    }
                }

                HorizontalDivider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredRecipes, key = { it.id }) { recipe ->
                        val proteinUsedCount = proteinCounts[recipe.protein] ?: 0
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectRecipe(recipe) },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = recipe.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
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
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "${recipe.protein.displayName} (in plan: ${proteinUsedCount}x)",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (proteinUsedCount >= 2) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        },
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "• ${recipe.prepAndCookMinutes} min",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                if (recipe.suggestedSides.isNotEmpty()) {
                                    Text(
                                        text = "Sides: ${recipe.suggestedSides.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
