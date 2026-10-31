package com.myweektocook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

enum class ProteinType(val displayName: String) {
    BEEF("Beef"),
    CHICKEN("Chicken"),
    PORK("Pork"),
    SEAFOOD("Seafood / Fish"),
    VEGETARIAN("Vegetarian"),
    OTHER("Other")
}

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val protein: ProteinType,
    val prepAndCookMinutes: Int,
    val ingredients: List<String>,
    val instructions: List<String>,
    val suggestedSides: List<String>,
    val isTrial: Boolean = false
)

@Entity(tableName = "planned_dinners")
data class PlannedDinnerEntity(
    @PrimaryKey
    val slotIndex: Int,
    val dayLabel: String,
    val isTimeLimited: Boolean = false,
    val recipeId: Long? = null,
    val selectedSides: List<String> = emptyList(),
    val isCooked: Boolean = false
)

@Entity(tableName = "shopping_items")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val sourceLabel: String,
    val isChecked: Boolean = false,
    val sortOrder: Int = 0,
    val isWeeklyStaple: Boolean = false
)

@Entity(tableName = "weekly_staples")
data class WeeklyStapleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val isEnabled: Boolean = true
)

class Converters {
    private val separator = "\n|||\n"

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        if (data.isBlank()) return emptyList()
        return data.split(separator).map { it.trim() }.filter { it.isNotEmpty() }
    }

    @TypeConverter
    fun fromProtein(protein: ProteinType): String = protein.name

    @TypeConverter
    fun toProtein(value: String): ProteinType =
        runCatching { ProteinType.valueOf(value) }.getOrDefault(ProteinType.OTHER)
}

object PantryFilter {
    private val exactOrPureSpices = listOf(
        "salt",
        "kosher salt",
        "sea salt",
        "black pepper",
        "freshly ground black pepper",
        "pepper",
        "olive oil",
        "extra-virgin olive oil",
        "vegetable oil",
        "oil",
        "water",
        "garlic powder",
        "onion powder",
        "garlic salt",
        "smoked paprika",
        "paprika",
        "chili powder",
        "chilli powder",
        "cumin",
        "ground cumin",
        "dried oregano",
        "oregano",
        "red pepper flakes",
        "red-pepper flakes",
        "ground ginger"
    )

    /**
     * Checks if an ingredient line from a recipe is a common pantry staple
     * (like salt, oil, water, or common dry spices) that shouldn't be added
     * to the shopping list by default.
     */
    fun isPantryStaple(ingredientLine: String): Boolean {
        val normalized = ingredientLine
            .lowercase()
            .replace(Regex("[^a-z\\s-]"), " ")
            .replace(
                Regex("\\b(tablespoons?|tbsp|teaspoons?|tsp|cups?|to taste|plus more|for seasoning|divided|optional)\\b"),
                " "
            )
            .replace(Regex("\\s+"), " ")
            .trim()

        if (normalized.isEmpty()) return true
        if (normalized in exactOrPureSpices) return true
        if (normalized.startsWith("salt and") || normalized.contains("salt, black pepper")) return true
        if (normalized.contains("garlic salt, paprika, pepper")) return true
        if (normalized.contains("sea salt, cracked black pepper")) return true
        return false
    }
}
