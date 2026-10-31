package com.myweektocook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // --- Recipes ---
    @Query("SELECT * FROM recipes ORDER BY isTrial DESC, title ASC")
    fun observeRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecipe(recipe: RecipeEntity): Long

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("UPDATE recipes SET isTrial = 0 WHERE id = :recipeId")
    suspend fun promoteTrialRecipe(recipeId: Long)

    // --- Planned Dinners ---
    @Query("SELECT * FROM planned_dinners ORDER BY slotIndex ASC")
    fun observePlannedDinners(): Flow<List<PlannedDinnerEntity>>

    @Query("SELECT * FROM planned_dinners ORDER BY slotIndex ASC")
    suspend fun getPlannedDinnersSnapshot(): List<PlannedDinnerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlannedDinner(dinner: PlannedDinnerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlannedDinners(dinners: List<PlannedDinnerEntity>)

    @Query("DELETE FROM planned_dinners WHERE slotIndex >= :targetCount")
    suspend fun trimPlannedDinnersAbove(targetCount: Int)

    @Query("DELETE FROM planned_dinners")
    suspend fun clearAllPlannedDinners()

    // --- Shopping List ---
    /**
     * Checked items remain visible and automatically move to the bottom of the list!
     * Unchecked items stay ordered by sortOrder.
     */
    @Query("SELECT * FROM shopping_items ORDER BY isChecked ASC, sortOrder ASC, id ASC")
    fun observeShoppingItems(): Flow<List<ShoppingItemEntity>>

    @Query("SELECT * FROM shopping_items ORDER BY isChecked ASC, sortOrder ASC, id ASC")
    suspend fun getShoppingItemsSnapshot(): List<ShoppingItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(items: List<ShoppingItemEntity>)

    @Update
    suspend fun updateShoppingItems(items: List<ShoppingItemEntity>)

    @Delete
    suspend fun deleteShoppingItem(item: ShoppingItemEntity)

    @Query("DELETE FROM shopping_items WHERE isChecked = 1")
    suspend fun clearCheckedShoppingItems()

    @Query("DELETE FROM shopping_items")
    suspend fun clearAllShoppingItems()

    // --- Weekly Staples ---
    @Query("SELECT * FROM weekly_staples ORDER BY id ASC")
    fun observeWeeklyStaples(): Flow<List<WeeklyStapleEntity>>

    @Query("SELECT * FROM weekly_staples ORDER BY id ASC")
    suspend fun getWeeklyStaplesSnapshot(): List<WeeklyStapleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyStaples(staples: List<WeeklyStapleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWeeklyStaple(staple: WeeklyStapleEntity)

    @Delete
    suspend fun deleteWeeklyStaple(staple: WeeklyStapleEntity)
}
