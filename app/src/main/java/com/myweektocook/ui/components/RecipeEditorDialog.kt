package com.myweektocook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.myweektocook.data.ProteinType
import com.myweektocook.data.RecipeEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeEditorDialog(
    initialRecipe: RecipeEntity? = null,
    defaultIsTrial: Boolean = true,
    onDismiss: () -> Unit,
    onSave: (
        id: Long,
        title: String,
        protein: ProteinType,
        prepAndCookMinutes: Int,
        ingredients: List<String>,
        instructions: List<String>,
        suggestedSides: List<String>,
        isTrial: Boolean
    ) -> Unit
) {
    var title by remember { mutableStateOf(initialRecipe?.title ?: "") }
    var protein by remember { mutableStateOf(initialRecipe?.protein ?: ProteinType.CHICKEN) }
    var minutesText by remember { mutableStateOf((initialRecipe?.prepAndCookMinutes ?: 30).toString()) }
    var isTrial by remember { mutableStateOf(initialRecipe?.isTrial ?: defaultIsTrial) }
    var sidesText by remember {
        mutableStateOf(initialRecipe?.suggestedSides?.joinToString(", ") ?: "Side Salad, Roasted Veggies")
    }
    var ingredientsText by remember {
        mutableStateOf(initialRecipe?.ingredients?.joinToString("\n") ?: "")
    }
    var instructionsText by remember {
        mutableStateOf(initialRecipe?.instructions?.joinToString("\n") ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialRecipe == null) "Add Recipe" else "Edit Recipe",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Recipe Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Trial / New Recipe",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Keep permanently if good, or discard after cooking",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isTrial,
                        onCheckedChange = { isTrial = it }
                    )
                }

                Text(
                    text = "Primary Protein",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ProteinType.entries.forEach { type ->
                        FilterChip(
                            selected = protein == type,
                            onClick = { protein = type },
                            label = { Text(type.displayName) }
                        )
                    }
                }

                OutlinedTextField(
                    value = minutesText,
                    onValueChange = { minutesText = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Total Prep + Cook Time (minutes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = sidesText,
                    onValueChange = { sidesText = it },
                    label = { Text("Suggested Sides (comma-separated)") },
                    placeholder = { Text("e.g. Side Salad, Garlic Bread, Rice") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ingredientsText,
                    onValueChange = { ingredientsText = it },
                    label = { Text("Ingredients (one per line)") },
                    placeholder = { Text("1 lb chicken breast\n2 cloves garlic\n...") },
                    minLines = 4,
                    maxLines = 7,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = instructionsText,
                    onValueChange = { instructionsText = it },
                    label = { Text("Instructions (one step per line)") },
                    placeholder = { Text("1. Preheat oven to 400F\n2. Season and bake...") },
                    minLines = 4,
                    maxLines = 7,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        },
        confirmButton = {
            TextButton(
                enabled = title.isNotBlank(),
                onClick = {
                    val minutes = minutesText.toIntOrNull() ?: 30
                    val sides = sidesText.split(",", "\n")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                    val ingredients = ingredientsText.lines()
                        .map { it.trim().removePrefix("-").removePrefix("*").trim() }
                        .filter { it.isNotEmpty() }
                    val instructions = instructionsText.lines()
                        .map { it.trim().replace(Regex("^(\\d+[.)]|Step\\s+\\d+:?)\\s*"), "").trim() }
                        .filter { it.isNotEmpty() }

                    onSave(
                        initialRecipe?.id ?: 0L,
                        title,
                        protein,
                        minutes,
                        ingredients,
                        instructions,
                        sides,
                        isTrial
                    )
                }
            ) {
                Text("Save Recipe", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
