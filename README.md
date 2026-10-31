# My Week to Cook

**My Week to Cook** is an Android meal-planning companion built for a bi-weekly cooking routine. Everything is stored locally on your device and organized around the three phases of getting dinner on the table: **Planning**, **Shopping**, and **Cooking**.

---

## App Navigation Overview

The bottom navigation bar gives you one-tap access to each phase of your week plus your full recipe collection:

| Tab | Purpose |
| :--- | :--- |
| **1. Plan** | Choose how many dinners you're making, flag busy nights, pick main courses & sides, and check protein variety. |
| **2. Shop** | View and reorder your grocery list, manage recurring weekly staples, and check items off in the store. |
| **3. Cook** | Follow step-by-step cooking instructions with a collapsible ingredients checklist and rate new trial recipes. |
| **Recipes** | Browse, search, filter, add, or edit recipes in your permanent collection or trial list. |

---

## How to Use the App

### Phase 1: Planning (`1. Plan` Tab)
Every other week when it's your turn to cook, start in the **1. Plan** tab to build your dinner list:

1. **Set Your Dinner Count**: Select how many dinners you are making this week (`3`, `4`, `5`, or `6`).
2. **Label Days & Flag Time-Limited Nights**:
   - Tap the pencil icon next to any dinner slot (e.g., `Dinner 1`) to rename it to a specific day like `Monday` or `Busy Wednesday`.
   - Toggle **Limited Time (≤35m)** on nights when you get home late from work. When you pick a recipe for that slot, the app automatically filters for quick recipes (`≤ 35 min`) and warns you if a longer recipe is selected.
3. **Select Main Courses & Try New Recipes**:
   - Tap **Pick Main Course Recipe** on any slot to search your collection or filter by protein and cook time.
   - Want to test a new dish? Tap **Research / Add New Trial Recipe** inside the picker to enter a new recipe marked with a `TRIAL` badge.
4. **Check Protein Variety**:
   - The **Protein Variety Check** banner at the top of the screen tracks how many times each protein (`Beef`, `Chicken`, `Pork`, `Seafood / Fish`, `Vegetarian`) appears in your plan.
   - It highlights when a protein is used `2x` and warns you if you exceed `2x` in the same week.
5. **Pick 1–2 Sides per Dinner**:
   - Each main course comes with suggested lightweight sides (e.g., *Side Salad*, *Garlic Bread*, *Roasted Asparagus*). Tap the chips on the dinner card to select 1 or 2 sides, or tap **+ Custom Side** to add your own.
   - The top summary card confirms once all planned dinners have sides attached.
6. **Send to Shopping List**:
   - Once your dinners are set, tap **Send Ingredients + Staples to Shopping List**. This pushes all recipe ingredients (automatically skipping common pantry items like salt, black pepper, oil, and basic spices) along with your recurring weekly staples directly to the **2. Shop** tab.
   - Starting a fresh cycle? Tap the **Reset (`↻`)** icon in the top-right of the Plan card to clear the week's plan.

---

### Phase 2: Shopping (`2. Shop` Tab)
When you head to the store on the weekend, open the **2. Shop** tab:

1. **Weekly Staples Included Automatically**:
   - Your recurring weekly staples (**Apples**, **Bananas**, **Milk**, **Eggs**, **Cheese**) are automatically added alongside your dinner ingredients when you sync your plan.
   - Tap the **Settings (`⚙`)** icon in the Shopping header to toggle, add, or remove items from your recurring weekly staples list.
2. **Add Extra Items**:
   - Use the **Add item** bar at the top to quickly add any extra household or grocery items.
3. **Reorder for Your Store Layout**:
   - Use the **`↑`** and **`↓`** arrows on any unchecked item to arrange your grocery list in the order you walk through the store.
4. **Check Items Off as You Shop**:
   - Tap an item's checkbox when you put it in your cart.
   - **Checked items stay visible and move to the bottom** of the list with a strikethrough so you can easily review what you've already grabbed or uncheck an item if needed.
   - When your trip is finished, tap **Clear Checked** to clean up completed items.

---

### Phase 3: Cooking (`3. Cook` Tab)
Throughout the week when it's time to make dinner, open the **3. Cook** tab (or tap **Open in Cooking View →** directly from a planned dinner card):

1. **Switch Between Planned Dinners**:
   - Tap the dinner chips across the top of the screen to select tonight's meal.
   - The header card displays the main course title, total prep/cook time, primary protein, and a reminder of **Tonight's Sides**.
2. **Toggleable Ingredients List**:
   - While prepping, check off individual ingredients as you measure them out.
   - Once you start cooking and no longer need the ingredient list taking up screen space, tap **Hide** on the Ingredients card to collapse it (and tap **Show** anytime to bring it back).
3. **Interactive Step-by-Step Instructions**:
   - Tap any numbered instruction card as you complete a step to cross it off and keep your place.
   - Tap **Mark Cooked** in the header when dinner is finished.
4. **Keep or Discard Trial Recipes**:
   - If tonight's dinner is a **Trial Recipe**, a verdict banner appears:
     - Tap **Keep Permanently** if the dish turned out great — this promotes it into your permanent collection.
     - Tap **Discard** if you didn't care for it — this removes it from your collection.

---

### Managing Your Collection (`Recipes` Tab)
Open the **Recipes** tab anytime to:
- Browse all pre-loaded recipes (seeded from [recipes.md](./recipes.md)) and custom recipes.
- Search by recipe title or side dish, or filter by protein category.
- Tap **+ New Recipe** to manually add a new permanent or trial recipe, or tap the **Edit (`✎`)** icon on any recipe to update its cook time, ingredients, instructions, or suggested sides.

---

## Developer & Build Documentation

For prerequisites, local build and installation instructions, project structure, and an architecture diagram, see **[README_DEV.md](./README_DEV.md)**.
