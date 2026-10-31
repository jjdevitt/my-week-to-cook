package com.myweektocook.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myweektocook.data.AppDatabase
import com.myweektocook.data.PantryFilter
import com.myweektocook.data.PlannedDinnerEntity
import com.myweektocook.data.ProteinType
import com.myweektocook.data.RecipeEntity
import com.myweektocook.data.SeedRecipes
import com.myweektocook.data.ShoppingItemEntity
import com.myweektocook.data.WeeklyStapleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PlannedDinnerUiModel(
    val slot: PlannedDinnerEntity,
    val recipe: RecipeEntity?
)

data class MealPlanUiState(
    val recipes: List<RecipeEntity> = emptyList(),
    val plannedDinners: List<PlannedDinnerUiModel> = emptyList(),
    val shoppingItems: List<ShoppingItemEntity> = emptyList(),
    val weeklyStaples: List<WeeklyStapleEntity> = emptyList(),
    val selectedCookingSlotIndex: Int? = null,
    val proteinCounts: Map<ProteinType, Int> = emptyMap(),
    val lastSyncedItemCount: Int? = null
)

class MealPlanViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).appDao()
    private val selectedCookingSlot = MutableStateFlow<Int?>(null)
    private val lastSyncedCount = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<MealPlanUiState> = combine(
        dao.observeRecipes(),
        dao.observePlannedDinners(),
        dao.observeShoppingItems(),
        dao.observeWeeklyStaples(),
        selectedCookingSlot
    ) { recipes, dinners, shopping, staples, cookingSlot ->
        val recipeMap = recipes.associateBy { it.id }
        val dinnerModels = dinners.map { slot ->
            PlannedDinnerUiModel(
                slot = slot,
                recipe = slot.recipeId?.let { recipeMap[it] }
            )
        }
        val counts = mutableMapOf<ProteinType, Int>()
        dinnerModels.mapNotNull { it.recipe }.forEach { recipe ->
            counts[recipe.protein] = (counts[recipe.protein] ?: 0) + 1
        }

        val resolvedCookingSlot = when {
            cookingSlot != null && dinnerModels.any { it.slot.slotIndex == cookingSlot && it.recipe != null } -> cookingSlot
            else -> dinnerModels.firstOrNull { it.recipe != null && !it.slot.isCooked }?.slot?.slotIndex
                ?: dinnerModels.firstOrNull { it.recipe != null }?.slot?.slotIndex
        }

        MealPlanUiState(
            recipes = recipes,
            plannedDinners = dinnerModels,
            shoppingItems = shopping,
            weeklyStaples = staples,
            selectedCookingSlotIndex = resolvedCookingSlot,
            proteinCounts = counts,
            lastSyncedItemCount = lastSyncedCount.value
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MealPlanUiState()
    )

    init {
        viewModelScope.launch {
            seedDatabaseIfNeeded()
        }
    }

    private suspend fun seedDatabaseIfNeeded() {
        if (dao.getRecipeCount() == 0) {
            dao.insertRecipes(SeedRecipes.initialRecipes)
        }
        if (dao.getWeeklyStaplesSnapshot().isEmpty()) {
            dao.insertWeeklyStaples(
                SeedRecipes.defaultWeeklyStaples.map { WeeklyStapleEntity(name = it, isEnabled = true) }
            )
        }
        val existingDinners = dao.getPlannedDinnersSnapshot()
        if (existingDinners.isEmpty()) {
            setTargetDinnerCountInternal(4)
        }
    }

    // --- Planning Phase Actions ---

    fun setTargetDinnerCount(count: Int) {
        val coerced = count.coerceIn(1, 7)
        viewModelScope.launch {
            setTargetDinnerCountInternal(coerced)
        }
    }

    private suspend fun setTargetDinnerCountInternal(count: Int) {
        val current = dao.getPlannedDinnersSnapshot().associateBy { it.slotIndex }
        dao.trimPlannedDinnersAbove(count)
        val updated = (0 until count).map { index ->
            current[index] ?: PlannedDinnerEntity(
                slotIndex = index,
                dayLabel = "Dinner ${index + 1}",
                isTimeLimited = false,
                recipeId = null,
                selectedSides = emptyList(),
                isCooked = false
            )
        }
        dao.upsertPlannedDinners(updated)
    }

    fun updateDayLabel(slotIndex: Int, newLabel: String) {
        viewModelScope.launch {
            val existing = dao.getPlannedDinnersSnapshot().find { it.slotIndex == slotIndex } ?: return@launch
            dao.upsertPlannedDinner(existing.copy(dayLabel = newLabel.ifBlank { "Dinner ${slotIndex + 1}" }))
        }
    }

    fun toggleTimeLimited(slotIndex: Int) {
        viewModelScope.launch {
            val existing = dao.getPlannedDinnersSnapshot().find { it.slotIndex == slotIndex } ?: return@launch
            dao.upsertPlannedDinner(existing.copy(isTimeLimited = !existing.isTimeLimited))
        }
    }

    fun assignRecipeToSlot(slotIndex: Int, recipe: RecipeEntity?) {
        viewModelScope.launch {
            val existing = dao.getPlannedDinnersSnapshot().find { it.slotIndex == slotIndex } ?: return@launch
            val defaultSides = recipe?.suggestedSides?.take(2) ?: emptyList()
            dao.upsertPlannedDinner(
                existing.copy(
                    recipeId = recipe?.id,
                    selectedSides = defaultSides,
                    isCooked = false
                )
            )
        }
    }

    fun toggleSideForDinner(slotIndex: Int, side: String) {
        val trimmed = side.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            val existing = dao.getPlannedDinnersSnapshot().find { it.slotIndex == slotIndex } ?: return@launch
            val currentSides = existing.selectedSides.toMutableList()
            if (currentSides.any { it.equals(trimmed, ignoreCase = true) }) {
                currentSides.removeAll { it.equals(trimmed, ignoreCase = true) }
            } else {
                currentSides.add(trimmed)
            }
            dao.upsertPlannedDinner(existing.copy(selectedSides = currentSides))
        }
    }

    fun startNewWeek(clearShoppingList: Boolean) {
        viewModelScope.launch {
            val currentCount = dao.getPlannedDinnersSnapshot().size.coerceIn(3, 5)
            dao.clearAllPlannedDinners()
            setTargetDinnerCountInternal(currentCount)
            if (clearShoppingList) {
                dao.clearAllShoppingItems()
            }
        }
    }

    /**
     * Pushes all planned dinner ingredients (excluding common pantry spices/salt/oil)
     * plus enabled weekly staples (apples, bananas, milk, eggs, cheese) onto the shopping list.
     */
    fun generateShoppingListFromPlan(onComplete: (Int) -> Unit = {}) {
        viewModelScope.launch {
            val existingItems = dao.getShoppingItemsSnapshot()
            val existingNames = existingItems.map { it.name.lowercase().trim() }.toMutableSet()
            var nextSortOrder = (existingItems.maxOfOrNull { it.sortOrder } ?: -1) + 1
            val newItems = mutableListOf<ShoppingItemEntity>()

            // 1. Add enabled weekly staples first
            val staples = dao.getWeeklyStaplesSnapshot().filter { it.isEnabled }
            for (staple in staples) {
                val key = staple.name.lowercase().trim()
                if (key !in existingNames) {
                    existingNames.add(key)
                    newItems.add(
                        ShoppingItemEntity(
                            name = staple.name.trim(),
                            sourceLabel = "Weekly Staple",
                            isChecked = false,
                            sortOrder = nextSortOrder++,
                            isWeeklyStaple = true
                        )
                    )
                }
            }

            // 2. Add ingredients from all planned dinners (filtering out salt, oil, water, common spices)
            val state = uiState.value
            for (planned in state.plannedDinners) {
                val recipe = planned.recipe ?: continue
                for (ingredient in recipe.ingredients) {
                    if (PantryFilter.isPantryStaple(ingredient)) continue
                    val key = ingredient.lowercase().trim()
                    if (key !in existingNames) {
                        existingNames.add(key)
                        newItems.add(
                            ShoppingItemEntity(
                                name = ingredient.trim(),
                                sourceLabel = recipe.title,
                                isChecked = false,
                                sortOrder = nextSortOrder++,
                                isWeeklyStaple = false
                            )
                        )
                    }
                }
            }

            if (newItems.isNotEmpty()) {
                dao.insertShoppingItems(newItems)
            }
            lastSyncedCount.value = newItems.size
            onComplete(newItems.size)
        }
    }

    // --- Recipe Management Actions (Manual New / Trial or Permanent Recipes) ---

    fun saveRecipe(
        id: Long = 0L,
        title: String,
        protein: ProteinType,
        prepAndCookMinutes: Int,
        ingredients: List<String>,
        instructions: List<String>,
        suggestedSides: List<String>,
        isTrial: Boolean,
        assignToSlotIndex: Int? = null
    ) {
        viewModelScope.launch {
            val entity = RecipeEntity(
                id = id,
                title = title.trim(),
                protein = protein,
                prepAndCookMinutes = prepAndCookMinutes.coerceAtLeast(5),
                ingredients = ingredients.map { it.trim() }.filter { it.isNotEmpty() },
                instructions = instructions.map { it.trim() }.filter { it.isNotEmpty() },
                suggestedSides = suggestedSides.map { it.trim() }.filter { it.isNotEmpty() },
                isTrial = isTrial
            )
            val savedId = dao.upsertRecipe(entity)
            val finalRecipe = entity.copy(id = if (id == 0L) savedId else id)
            if (assignToSlotIndex != null) {
                assignRecipeToSlot(assignToSlotIndex, finalRecipe)
            }
        }
    }

    fun promoteTrialRecipeToPermanent(recipe: RecipeEntity) {
        viewModelScope.launch {
            dao.promoteTrialRecipe(recipe.id)
        }
    }

    fun discardRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            // Clear any planned dinner slots referencing this discarded recipe
            val dinners = dao.getPlannedDinnersSnapshot()
            dinners.filter { it.recipeId == recipe.id }.forEach { slot ->
                dao.upsertPlannedDinner(slot.copy(recipeId = null, selectedSides = emptyList(), isCooked = false))
            }
            dao.deleteRecipe(recipe)
        }
    }

    // --- Shopping Phase Actions ---

    fun toggleShoppingItemChecked(item: ShoppingItemEntity) {
        viewModelScope.launch {
            val allItems = dao.getShoppingItemsSnapshot()
            val maxOrder = allItems.maxOfOrNull { it.sortOrder } ?: 0
            val updated = item.copy(
                isChecked = !item.isChecked,
                sortOrder = if (!item.isChecked) maxOrder + 1 else item.sortOrder
            )
            dao.updateShoppingItems(listOf(updated))
        }
    }

    fun moveShoppingItem(item: ShoppingItemEntity, moveUp: Boolean) {
        viewModelScope.launch {
            val unchecked = dao.getShoppingItemsSnapshot()
                .filter { !it.isChecked }
                .sortedWith(compareBy({ it.sortOrder }, { it.id }))
                .toMutableList()

            val index = unchecked.indexOfFirst { it.id == item.id }
            if (index == -1) return@launch
            val targetIndex = if (moveUp) index - 1 else index + 1
            if (targetIndex !in unchecked.indices) return@launch

            val swapped = unchecked[targetIndex]
            unchecked[targetIndex] = unchecked[index]
            unchecked[index] = swapped

            val reindexed = unchecked.mapIndexed { idx, shoppingItem ->
                shoppingItem.copy(sortOrder = idx)
            }
            dao.updateShoppingItems(reindexed)
        }
    }

    fun addCustomShoppingItem(name: String, sourceLabel: String = "Added Manually") {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            val allItems = dao.getShoppingItemsSnapshot()
            val minOrder = (allItems.minOfOrNull { it.sortOrder } ?: 0) - 1
            dao.insertShoppingItem(
                ShoppingItemEntity(
                    name = trimmed,
                    sourceLabel = sourceLabel,
                    isChecked = false,
                    sortOrder = minOrder,
                    isWeeklyStaple = false
                )
            )
        }
    }

    fun deleteShoppingItem(item: ShoppingItemEntity) {
        viewModelScope.launch {
            dao.deleteShoppingItem(item)
        }
    }

    fun clearCheckedShoppingItems() {
        viewModelScope.launch {
            dao.clearCheckedShoppingItems()
        }
    }

    fun clearAllShoppingItems() {
        viewModelScope.launch {
            dao.clearAllShoppingItems()
        }
    }

    fun addWeeklyStaple(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            dao.upsertWeeklyStaple(WeeklyStapleEntity(name = trimmed, isEnabled = true))
        }
    }

    fun toggleWeeklyStapleEnabled(staple: WeeklyStapleEntity) {
        viewModelScope.launch {
            dao.upsertWeeklyStaple(staple.copy(isEnabled = !staple.isEnabled))
        }
    }

    fun deleteWeeklyStaple(staple: WeeklyStapleEntity) {
        viewModelScope.launch {
            dao.deleteWeeklyStaple(staple)
        }
    }

    // --- Cooking Phase Actions ---

    fun selectCookingSlot(slotIndex: Int) {
        selectedCookingSlot.value = slotIndex
    }

    fun toggleDinnerCooked(slotIndex: Int) {
        viewModelScope.launch {
            val existing = dao.getPlannedDinnersSnapshot().find { it.slotIndex == slotIndex } ?: return@launch
            dao.upsertPlannedDinner(existing.copy(isCooked = !existing.isCooked))
        }
    }
}
