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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.myweektocook.data.ShoppingItemEntity
import com.myweektocook.ui.MealPlanUiState
import com.myweektocook.ui.MealPlanViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShoppingScreen(
    uiState: MealPlanUiState,
    viewModel: MealPlanViewModel
) {
    var customItemText by remember { mutableStateOf("") }
    var showStaplesDialog by remember { mutableStateOf(false) }

    val uncheckedItems = uiState.shoppingItems.filter { !it.isChecked }
    val checkedItems = uiState.shoppingItems.filter { it.isChecked }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // --- Header & Actions Card ---
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
                                text = "Grocery Shopping List",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${uncheckedItems.size} to buy • ${checkedItems.size} checked off (at bottom)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        IconButton(onClick = { showStaplesDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Configure Weekly Staples"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.generateShoppingListFromPlan() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Sync Plan & Staples")
                        }
                        if (checkedItems.isNotEmpty()) {
                            OutlinedButton(
                                onClick = { viewModel.clearCheckedShoppingItems() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteSweep,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Clear Checked")
                            }
                        }
                    }

                    // Add Custom Grocery Item Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = customItemText,
                            onValueChange = { customItemText = it },
                            placeholder = { Text("Add item (e.g. Paper towels, Snacks)...") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            enabled = customItemText.isNotBlank(),
                            onClick = {
                                viewModel.addCustomShoppingItem(customItemText)
                                customItemText = ""
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        }
                    }

                    Text(
                        text = "Note: Common pantry items (salt, pepper, oil, basic dry spices) are automatically skipped when syncing recipes.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // --- Empty State ---
        if (uiState.shoppingItems.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Your shopping list is empty",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tap 'Sync Plan & Staples' above to pull ingredients from your planned dinners plus weekly staples (apples, bananas, milk, eggs, cheese).",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // --- Unchecked Items (Reorderable) ---
        itemsIndexed(
            items = uncheckedItems,
            key = { _, item -> item.id }
        ) { index, item ->
            ShoppingItemRow(
                item = item,
                canMoveUp = index > 0,
                canMoveDown = index < uncheckedItems.lastIndex,
                onToggleChecked = { viewModel.toggleShoppingItemChecked(item) },
                onMoveUp = { viewModel.moveShoppingItem(item, moveUp = true) },
                onMoveDown = { viewModel.moveShoppingItem(item, moveUp = false) },
                onDelete = { viewModel.deleteShoppingItem(item) }
            )
        }

        // --- Divider before Checked Items at Bottom ---
        if (checkedItems.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    HorizontalDivider()
                    Text(
                        text = "Checked Off (${checkedItems.size})",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            itemsIndexed(
                items = checkedItems,
                key = { _, item -> item.id }
            ) { _, item ->
                ShoppingItemRow(
                    item = item,
                    canMoveUp = false,
                    canMoveDown = false,
                    onToggleChecked = { viewModel.toggleShoppingItemChecked(item) },
                    onMoveUp = {},
                    onMoveDown = {},
                    onDelete = { viewModel.deleteShoppingItem(item) }
                )
            }
        }
    }

    // --- Weekly Staples Configuration Dialog ---
    if (showStaplesDialog) {
        var newStapleName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showStaplesDialog = false },
            title = {
                Text("Weekly Staples", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Items you buy every week (included whenever you sync your shopping list):",
                        style = MaterialTheme.typography.bodySmall
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        uiState.weeklyStaples.forEach { staple ->
                            FilterChip(
                                selected = staple.isEnabled,
                                onClick = { viewModel.toggleWeeklyStapleEnabled(staple) },
                                label = { Text(staple.name) },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { viewModel.deleteWeeklyStaple(staple) },
                                        modifier = Modifier.size(16.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove staple",
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedTextField(
                            value = newStapleName,
                            onValueChange = { newStapleName = it },
                            placeholder = { Text("Add weekly staple...") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(
                            enabled = newStapleName.isNotBlank(),
                            onClick = {
                                viewModel.addWeeklyStaple(newStapleName)
                                newStapleName = ""
                            }
                        ) {
                            Text("Add")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showStaplesDialog = false }) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
private fun ShoppingItemRow(
    item: ShoppingItemEntity,
    canMoveUp: Boolean,
    canMoveDown: Boolean,
    onToggleChecked: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isChecked) {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (item.isChecked) 0.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onToggleChecked() }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (item.isChecked) FontWeight.Normal else FontWeight.SemiBold,
                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (item.isChecked) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Surface(
                    color = if (item.isWeeklyStaple) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = item.sourceLabel,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            if (!item.isChecked) {
                IconButton(
                    onClick = onMoveUp,
                    enabled = canMoveUp,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Move Up",
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = onMoveDown,
                    enabled = canMoveDown,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = "Move Down",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Item",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
