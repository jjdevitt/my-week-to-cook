package com.myweektocook

import com.myweektocook.data.Converters
import com.myweektocook.data.PantryFilter
import com.myweektocook.data.SeedRecipes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MealPlanningLogicTest {

    @Test
    fun pantryFilter_excludesSaltOilWaterAndCommonSpices() {
        assertTrue(PantryFilter.isPantryStaple("1 teaspoon salt"))
        assertTrue(PantryFilter.isPantryStaple("2 tablespoons olive oil"))
        assertTrue(PantryFilter.isPantryStaple("Salt, black pepper, and garlic powder to taste"))
        assertTrue(PantryFilter.isPantryStaple("1 tablespoon smoked paprika"))
        assertTrue(PantryFilter.isPantryStaple("1 cup water"))

        assertFalse(PantryFilter.isPantryStaple("1 lb ground beef"))
        assertFalse(PantryFilter.isPantryStaple("4 boneless, skinless chicken breasts"))
        assertFalse(PantryFilter.isPantryStaple("1 cup shredded cheddar cheese"))
        assertFalse(PantryFilter.isPantryStaple("Smoked salmon"))
    }

    @Test
    fun seedRecipes_allHaveIngredientsInstructionsTimeAndSuggestedSides() {
        assertTrue(SeedRecipes.initialRecipes.size >= 18)
        SeedRecipes.initialRecipes.forEach { recipe ->
            assertTrue("Recipe ${recipe.title} missing ingredients", recipe.ingredients.isNotEmpty())
            assertTrue("Recipe ${recipe.title} missing instructions", recipe.instructions.isNotEmpty())
            assertTrue("Recipe ${recipe.title} missing sides", recipe.suggestedSides.isNotEmpty())
            assertTrue("Recipe ${recipe.title} invalid time", recipe.prepAndCookMinutes > 0)
        }
    }

    @Test
    fun defaultWeeklyStaples_matchesReadMe() {
        assertEquals(
            listOf("Apples", "Bananas", "Milk", "Eggs", "Cheese"),
            SeedRecipes.defaultWeeklyStaples
        )
    }

    @Test
    fun converters_roundTripStringLists() {
        val converters = Converters()
        val original = listOf("Side Salad", "Garlic Bread", "Roasted Asparagus")
        val serialized = converters.fromStringList(original)
        val restored = converters.toStringList(serialized)
        assertEquals(original, restored)
    }
}
