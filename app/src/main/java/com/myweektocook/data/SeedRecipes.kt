package com.myweektocook.data

object SeedRecipes {

    val defaultWeeklyStaples = listOf(
        "Apples",
        "Bananas",
        "Milk",
        "Eggs",
        "Cheese"
    )

    val initialRecipes: List<RecipeEntity> = listOf(
        RecipeEntity(
            title = "Slow Cooker Pulled Pork",
            protein = ProteinType.PORK,
            prepAndCookMinutes = 360, // 15 min prep + slow cooker (good for prep-ahead, or note slow cook)
            ingredients = listOf(
                "4-5 pounds pork shoulder, cut into chunks",
                "1 tablespoon smoked paprika",
                "1 tablespoon garlic powder",
                "1 tablespoon onion powder",
                "1 tablespoon brown sugar",
                "1 teaspoon salt",
                "1/2 teaspoon black pepper",
                "1 cup water",
                "2 cups BBQ sauce",
                "Hawaiian buns"
            ),
            instructions = listOf(
                "Season and Brown the Pork: In a bowl, combine smoked paprika, garlic powder, onion powder, brown sugar, salt, and black pepper. Rub the spice mixture over the pork chunks. Optionally, brown the seasoned pork in a skillet for extra flavor.",
                "Slow Cook: Place the seasoned or browned pork in the slow cooker. Cover and cook on low for 8-10 hours or on high for 4-6 hours, or until the pork is tender and easily shreds.",
                "Shred the Pork: Remove the pork from the slow cooker and shred it using two forks.",
                "Add BBQ Sauce: Pour 2 cups of your favorite store-bought BBQ sauce over the shredded pork. Mix the BBQ sauce with the shredded pork, incorporating it evenly.",
                "Serve: Serve the Slow Cooker Pulled Pork on buns or as desired."
            ),
            suggestedSides = listOf("Coleslaw", "Kettle Chips", "Baked Beans", "Corn on the Cob")
        ),
        RecipeEntity(
            title = "Tater Tot Hot Dish",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 55,
            ingredients = listOf(
                "1 lb ground beef",
                "1 small yellow onion, diced",
                "2 tbsp all-purpose flour",
                "1 cup chicken broth",
                "1 cup sliced mushrooms",
                "1 cup frozen mixed vegetables (corn, peas, green beans)",
                "1 cup shredded cheddar cheese",
                "1 bag (16-32 oz) frozen tater tots",
                "Salt, black pepper, and garlic powder to taste"
            ),
            instructions = listOf(
                "Prep: Preheat oven to 400°F.",
                "Brown Beef & Onion: In a large skillet over medium-high heat, brown the ground beef with diced onion until fully cooked. Drain excess fat. Season with salt, pepper, and garlic powder.",
                "Make Sauce: Sprinkle the flour over the beef and onion mixture. Stir continuously for 1–2 minutes to cook off the raw flour taste. Slowly pour in chicken broth and milk, stirring constantly until smooth. Bring to a simmer and stir until thickened.",
                "Combine: Remove from heat and stir in the frozen mixed vegetables and half of the shredded cheddar cheese.",
                "Assemble: Top with an even layer of tater tots, then sprinkle the remaining cheese over top.",
                "Bake: Bake uncovered for 35–40 minutes, or until the tater tots are golden brown, crispy, and the sauce is bubbling around the edges.",
                "Top with cheese and cook for 5 more minutes.",
                "Serve: Let cool for 5 minutes before serving."
            ),
            suggestedSides = listOf("Side Salad", "Dinner Rolls", "Roasted Green Beans")
        ),
        RecipeEntity(
            title = "Garlic Parm Chicken Potato Skillet",
            protein = ProteinType.CHICKEN,
            prepAndCookMinutes = 35,
            ingredients = listOf(
                "2 chicken breasts",
                "3 Russet potatoes",
                "garlic salt, paprika, pepper",
                "mozzarella",
                "Grated Parmesan Cheese"
            ),
            instructions = listOf(
                "Cube potatoes, toss with Oil, Salt, Pepper, Paprika - airfry at 400°F for 30 minutes.",
                "Cube Chicken and cook in pan with butter (or oil), season with salt, pepper, paprika.",
                "Combine in cooked potatoes, and cheese."
            ),
            suggestedSides = listOf("Steamed Broccoli", "Caesar Salad", "Roasted Asparagus")
        ),
        RecipeEntity(
            title = "Chicken Piccata",
            protein = ProteinType.CHICKEN,
            prepAndCookMinutes = 25,
            ingredients = listOf(
                "4 boneless, skinless chicken breasts (about 1½ pounds), halved horizontally",
                "1 shallot, peeled and sliced lengthwise",
                "1 lemon, halved (half thinly sliced; half juiced, about 2 tablespoons)",
                "4 teaspoons drained capers",
                "Coarsely chopped fresh parsley, for garnish (optional)",
                "All-purpose flour (for dredging)",
                "6 tablespoons butter",
                "Chicken stock"
            ),
            instructions = listOf(
                "Season both sides of the chicken with salt and pepper. Dredge the chicken in flour and shake off any excess.",
                "In a large skillet, heat 3 tablespoons butter and the olive oil over medium-high heat. Working in batches, sauté chicken until golden brown, about 3 minutes per side.",
                "Remove chicken to a plate and repeat with remaining pieces.",
                "Add shallot and lemon slices to the pan; sauté until caramelized, 2 to 3 minutes. Add stock and simmer until reduced by half, about 3 minutes.",
                "Reduce heat to low; stir in remaining 3 tablespoons butter, capers, and lemon juice. Season to taste. Serve sauce over chicken and garnish with parsley."
            ),
            suggestedSides = listOf("Angel Hair Pasta", "Garlic Bread", "Roasted Green Beans", "Side Salad")
        ),
        RecipeEntity(
            title = "Loaded Baked Potato Soup",
            protein = ProteinType.PORK,
            prepAndCookMinutes = 30,
            ingredients = listOf(
                "4 potatoes, peeled and cubed",
                "1/2 cup butter",
                "1/2 cup all-purpose flour",
                "2 cups milk",
                "2 cups chicken broth",
                "1/4 cup chopped onion",
                "4 ounce sour cream",
                "1/2 cup shredded Cheddar cheese",
                "salt and pepper to taste",
                "bacon",
                "1 teaspoon chicken stock concentrate",
                "chives for garnish"
            ),
            instructions = listOf(
                "Place cubed potatoes into a glass dish, and cook in the microwave oven for 7 to 10 minutes, or until soft.",
                "While the potatoes are cooking, melt the butter in a large pot over medium-high heat. Whisk in flour until smooth, then gradually stir in the milk. Bring to a boil, then reduce heat to medium, and simmer for 5 to 10 minutes, or until slightly thickened.",
                "Stir in the potatoes and onion, and cook for 5 more minutes. Stir in the sour cream and Cheddar cheese until melted and well blended. Season with salt and pepper."
            ),
            suggestedSides = listOf("Crusty Bread", "Garden Salad", "Grilled Cheese")
        ),
        RecipeEntity(
            title = "Hoisin Garlic Noodles",
            protein = ProteinType.VEGETARIAN,
            prepAndCookMinutes = 20,
            ingredients = listOf(
                "14 ounces dried wheat or egg noodles",
                "¼ cup hoisin sauce",
                "2 tablespoons soy sauce",
                "1 tablespoon toasted sesame oil",
                "2 teaspoons maple syrup or honey",
                "6 medium garlic cloves, finely chopped",
                "6 scallions, white and green parts separated, thinly sliced",
                "4 tablespoons toasted white sesame seeds"
            ),
            instructions = listOf(
                "Bring a large pot of salted water to a boil. Add the noodles and cook according to package instructions until al dente. Drain and rinse until the noodles are cool.",
                "Meanwhile, in a small bowl, combine hoisin sauce, soy sauce, sesame oil and maple syrup; set aside.",
                "Heat a large 12-inch skillet on medium-high for 2 minutes. Add 1 to 2 tablespoons of oil along with the garlic and white parts of the scallions. Stir until fragrant, about 30 seconds (don't let the garlic burn!). Immediately add the sauce and then the noodles, and toss well until the noodles are evenly coated.",
                "Leave the noodles to cook, undisturbed, until they start sticking to the pan and the bottom looks crispy, 2 to 3 minutes. Taste and season with salt (if needed) and pepper.",
                "To serve, transfer to bowls and top with the sesame seeds and the green parts of the scallions."
            ),
            suggestedSides = listOf("Edamame", "Egg Rolls", "Stir-Fried Broccoli", "Cucumber Salad")
        ),
        RecipeEntity(
            title = "Sushi Salmon Bowls",
            protein = ProteinType.SEAFOOD,
            prepAndCookMinutes = 20,
            ingredients = listOf(
                "Rice",
                "Smoked salmon",
                "Avocado",
                "Cucumber",
                "Sliced carrots",
                "Sushi seasoning (furikake)"
            ),
            instructions = listOf(
                "Cook rice according to package or rice cooker instructions.",
                "Assemble a bowl with a base of rice, then top with smoked salmon, avocado, cucumber, sliced carrots, and a sprinkle of sushi seasoning (furikake)."
            ),
            suggestedSides = listOf("Miso Soup", "Edamame", "Seaweed Salad", "Gyoza")
        ),
        RecipeEntity(
            title = "Chicken Marsala",
            protein = ProteinType.CHICKEN,
            prepAndCookMinutes = 35,
            ingredients = listOf(
                "Chicken thighs",
                "2/3 cup Marsala wine",
                "2/3 cup half and half",
                "1/2 teaspoon Better Than Bouillon concentrated chicken broth",
                "Salt to taste",
                "Garlic",
                "1 small onion",
                "Mushrooms",
                "Butter",
                "Garlic salt",
                "Flour"
            ),
            instructions = listOf(
                "Put chicken thighs in a 1 gallon ziplock bag, add flour and garlic salt, shake to coat and pound flat.",
                "Add butter to a pan, cook chicken thighs until browned on both sides, then remove.",
                "Add mushrooms to the pan and brown, then add onions and garlic and stir.",
                "Add Marsala wine, chicken broth, and half and half to the sauce and cook 15 minutes until reduced.",
                "Add everything back to the pan and serve."
            ),
            suggestedSides = listOf("Mashed Potatoes", "Egg Noodles", "Roasted Asparagus", "Garlic Bread")
        ),
        RecipeEntity(
            title = "Stuffed Sweet Potatoes",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 50, // 40m air fry + 10m broil/prep
            ingredients = listOf(
                "2 medium sweet potatoes",
                "1 small onion, small diced",
                "2 cloves garlic, finely minced",
                "2 handfuls kale, torn and destemmed",
                "1 lb extra-lean ground beef",
                "1 can (15 oz) kidney beans, drained and rinsed",
                "Mozzarella cheese (grated)",
                "2–3 tsp olive oil",
                "1 tbsp tomato paste",
                "14.5 oz can crushed tomatoes",
                "½ to ¾ cup stock of choice",
                "1 tsp chili powder",
                "1 tsp cumin",
                "½ tsp smoked paprika",
                "1 tsp salt",
                "Sea salt, cracked black pepper, and chili flakes"
            ),
            instructions = listOf(
                "Bake the Sweet Potatoes: Preheat oven to 375°F (or air fry at 370°F for 35–40 minutes). Wash the sweet potatoes and prick them all over with a fork. Brush with olive oil and bake until fork-tender.",
                "Sauté the Aromatics and Beef: While the sweet potatoes bake, heat olive oil in a large skillet over medium-high heat. Add the diced onion and cook for 2–3 minutes before adding the minced garlic. Stir until fragrant, then add the ground beef. Break up the beef and cook until no longer pink. Season with salt, chili powder, cumin, and smoked paprika.",
                "Build the Chili Filling: Stir in the tomato paste and cook for 1 minute. Add the crushed tomatoes and kidney beans, mixing until bubbling. Stir in the stock and torn kale, letting everything simmer for a few minutes until the kale wilts and the mixture becomes thick.",
                "Stuff and Broil: Carefully slice the baked sweet potatoes in half. Lightly mash the flesh inside the skin and season with salt and pepper. Spoon the beef and bean mixture onto each potato half and top with grated mozzarella. Broil for 3–5 minutes until melted and bubbly."
            ),
            suggestedSides = listOf("Tortilla Chips & Salsa", "Side Salad", "Cornbread")
        ),
        RecipeEntity(
            title = "Lemon Dill Salmon",
            protein = ProteinType.SEAFOOD,
            prepAndCookMinutes = 30,
            ingredients = listOf(
                "Salmon",
                "2 tbsp Dill",
                "Zest from Lemon",
                "1/4 cup lemon Juice",
                "1 stick butter",
                "2 garlic cloves"
            ),
            instructions = listOf(
                "Preheat Oven to 400°F.",
                "Divide Salmon into filets, put skin down on large sheet of foil, season with salt and pepper.",
                "Melt butter into pan, add garlic, dill, lemon zest and juice.",
                "Pour sauce over Salmon, cover filets and add to oven for 25 minutes."
            ),
            suggestedSides = listOf("Rice Pilaf", "Roasted Asparagus", "Roasted Potatoes", "Side Salad")
        ),
        RecipeEntity(
            title = "Pork Bok Choy Noodles",
            protein = ProteinType.PORK,
            prepAndCookMinutes = 20,
            ingredients = listOf(
                "2x bok choy",
                "ground pork",
                "green onion",
                "wheat noodles",
                "soy sauce",
                "sesame oil",
                "chicken broth"
            ),
            instructions = listOf(
                "Brown ground pork in a skillet or wok.",
                "Add in chopped bok choy and green onion and sauté until tender-crisp.",
                "Add in cooked wheat noodles, soy sauce, sesame oil, and a splash of chicken broth; toss to combine."
            ),
            suggestedSides = listOf("Potstickers", "Cucumber Salad", "Spring Rolls")
        ),
        RecipeEntity(
            title = "Stuffed Red Peppers",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 65,
            ingredients = listOf(
                "4 large red, orange or yellow bell peppers",
                "2 tablespoons olive oil",
                "1 cup celery",
                "1 cup finely chopped yellow onion",
                "3 garlic cloves, minced",
                "1 teaspoon dried oregano",
                "½ teaspoon red-pepper flakes",
                "1 pound ground beef",
                "¾ cup dry white wine/chicken broth",
                "1 (14-ounce) can diced fire-roasted tomatoes",
                "1 cup cooked white or brown rice",
                "¼ cup grated Parmesan",
                "2 tablespoons minced parsley",
                "1 cup shredded mozzarella"
            ),
            instructions = listOf(
                "Heat the oven to 400°F. Cut the peppers in half lengthwise and carefully remove core, seeds and ribs. Arrange the peppers, cut-sides up, in a 9x13-inch baking dish.",
                "In a large skillet, heat the olive oil over medium. Add the celery and onions and cook until tender, 6 to 8 minutes. Stir in the garlic, oregano and red-pepper flakes, and cook for 1 minute.",
                "Add the beef and cook, breaking up the meat, for 3 to 5 minutes, until no longer pink.",
                "Add ½ cup wine/broth, increase heat to medium-high and cook until reduced by about half.",
                "Add the tomatoes and their juices, salt and pepper, and bring to a boil. Remove from heat and stir in the rice, Parmesan and parsley.",
                "Divide the mixture among the peppers. Pour the remaining ¼ cup wine/broth into the bottom of the dish.",
                "Wrap tightly with foil and bake for 35 to 40 minutes, until tender.",
                "Remove foil, sprinkle mozzarella evenly onto the peppers and bake another 10 to 15 minutes until melted and browned."
            ),
            suggestedSides = listOf("Garlic Bread", "Caesar Salad", "Roasted Zucchini")
        ),
        RecipeEntity(
            title = "Shepherd's Pie",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 70,
            ingredients = listOf(
                "2 tablespoons kosher salt",
                "2½ pounds russet potatoes, peeled and quartered",
                "8 tablespoons unsalted butter",
                "½ cup whole milk",
                "1 cup shredded aged white Cheddar",
                "Freshly ground black pepper",
                "1 small yellow onion, diced small",
                "2 medium carrots, peeled and diced small",
                "4 cloves garlic, minced",
                "4 sprigs fresh thyme",
                "Sprigs fresh rosemary",
                "¾ pound lean ground beef",
                "¾ pound ground lamb (or use all ground beef)",
                "⅓ cup tomato paste",
                "1 tablespoon all-purpose flour",
                "¾ cup beef stock",
                "1 cup fresh parsley, chopped"
            ),
            instructions = listOf(
                "In a large pot, bring a gallon of salted water to a boil. Add potatoes and boil for 15 to 20 minutes until soft.",
                "Heat 6 tablespoons butter and milk together until melted. Drain potatoes and mash until smooth. Mix in butter mixture and Cheddar. Season with salt and pepper.",
                "Heat oven to 375°F. Melt remaining 2 tablespoons butter in an oven-safe skillet over medium heat. Add onion, carrot, garlic, thyme and rosemary and cook for 10 minutes. Add beef and lamb and cook until no longer pink.",
                "Add tomato paste and cook 2 to 3 minutes. Sprinkle flour and cook 1 minute. Add beef stock and cook until thickened slightly. Stir in parsley and remove herb stems.",
                "Top the meat mixture with mashed potatoes and spread evenly. Bake for about 30 minutes until golden and bubbling. Let stand 15 minutes before serving."
            ),
            suggestedSides = listOf("Steamed Peas", "Dinner Rolls", "Side Salad")
        ),
        RecipeEntity(
            title = "Beef Bulgogi Bowls",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 30,
            ingredients = listOf(
                "1 lb flank steak, thinly sliced (against the grain)",
                "1/4 cup soy sauce",
                "2 tbsp brown sugar",
                "2 tbsp sesame oil",
                "2 cloves garlic, minced",
                "1 tbsp grated ginger",
                "1 green onion, chopped",
                "1 tbsp gochujang (Korean chili paste)",
                "2 cups cooked white rice",
                "1/2 cup kimchi, chopped",
                "1 cucumber, thinly sliced",
                "1 carrot, julienned",
                "1 avocado, sliced",
                "2 eggs, fried sunny-side up (optional)",
                "Sesame seeds, for garnish"
            ),
            instructions = listOf(
                "Marinate the beef: In a large bowl, combine soy sauce, brown sugar, sesame oil, garlic, ginger, green onion, and gochujang. Add thinly sliced beef and toss to coat. Marinate for at least 30 minutes.",
                "Cook the beef: Heat a large skillet over medium-high heat. Add marinated beef and cook until browned and cooked through, about 5-7 minutes.",
                "Assemble the bowls: Divide cooked rice between bowls. Top with kimchi, cucumber, carrot, avocado, and cooked bulgogi beef. Top with fried eggs if desired.",
                "Garnish with sesame seeds and sriracha or sesame oil."
            ),
            suggestedSides = listOf("Miso Soup", "Mandu / Dumplings", "Edamame")
        ),
        RecipeEntity(
            title = "Steak Diane",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 25,
            ingredients = listOf(
                "1 tablespoon unsalted butter",
                "1/2 tablespoon extra-virgin olive oil",
                "4 (3-ounce) beef tenderloin medallions, pounded 3/4-inch thick",
                "Kosher salt",
                "Freshly ground black pepper",
                "1 small shallot, minced",
                "1 garlic clove, minced",
                "1/4 pound button mushrooms, sliced 1/4-inch thick",
                "1/4 cup Cognac or other brandy",
                "2 teaspoons Dijon mustard",
                "1/4 cup heavy cream",
                "2 teaspoons Worcestershire sauce",
                "1 tablespoon finely chopped scallions",
                "1 teaspoon finely chopped fresh flat-leaf parsley",
                "Hot sauce, such as Tabasco"
            ),
            instructions = listOf(
                "In a large skillet, melt butter with olive oil.",
                "Season meat with salt and pepper and cook over high heat until lightly browned, about 1 minute per side. Transfer to a plate and tent with foil.",
                "Add shallot and garlic to skillet and cook for 20 seconds. Add mushrooms, season with salt and pepper, and cook until softened, about 2 minutes.",
                "Remove pan from heat, add Cognac, and carefully Simmer/flambé until reduced.",
                "Add mustard and cream and stir over moderate heat for 1 minute.",
                "Whisk in Worcestershire sauce, scallions, and parsley; season with salt, pepper, and hot sauce.",
                "Add meat and accumulated juices to saucepan and turn to coat. Simmer for 1 minute and serve."
            ),
            suggestedSides = listOf("Mashed Potatoes", "Roasted Asparagus", "Side Salad", "Crusty Bread")
        ),
        RecipeEntity(
            title = "Tuna Onigiri (Rice Balls)",
            protein = ProteinType.SEAFOOD,
            prepAndCookMinutes = 25,
            ingredients = listOf(
                "2¼ cups uncooked Japanese short-grain white rice",
                "Nori",
                "Canned Tuna",
                "Mayo",
                "Soy sauce",
                "Sriracha",
                "Furikake"
            ),
            instructions = listOf(
                "Cook rice in rice cooker.",
                "Mix mayo, drained tuna, soy sauce, and sriracha.",
                "Season warm rice with furikake, stuff with tuna filling, press into triangle/ball shapes, and wrap with nori."
            ),
            suggestedSides = listOf("Miso Soup", "Edamame", "Cucumber Sunomono")
        ),
        RecipeEntity(
            title = "Yaki Mandu",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 35,
            ingredients = listOf(
                "1 pound ground beef",
                "1/2 cup chopped onion",
                "1/4 cup chopped green pepper",
                "1/4 cup chopped carrot",
                "2 cloves garlic, minced",
                "1 tablespoon soy sauce",
                "1 teaspoon sesame oil",
                "1/2 teaspoon ground ginger",
                "1/4 teaspoon red pepper flakes (optional)",
                "1 package (30 ounces) wonton wrappers"
            ),
            instructions = listOf(
                "In a large bowl, combine the ground beef, onion, green pepper, carrot, garlic, soy sauce, sesame oil, ginger, and red pepper flakes. Mix well.",
                "Place a heaping tablespoon of filling in the center of each wonton wrapper. Wet the edges with water, fold over into a half-moon shape, and pinch to seal.",
                "Heat a large skillet over medium heat with a tablespoon of oil. Cook dumplings in a single layer for 2-3 minutes per side until golden brown and crispy.",
                "Serve immediately with dipping sauce (soy sauce, rice vinegar, sesame oil, and sriracha)."
            ),
            suggestedSides = listOf("Steamed Rice", "Kimchi", "Stir-Fried Bok Choy")
        ),
        RecipeEntity(
            title = "Chipotle Chicken Burritos",
            protein = ProteinType.CHICKEN,
            prepAndCookMinutes = 35,
            ingredients = listOf(
                "1 lb boneless, skinless chicken breasts, sliced into strips",
                "2 tablespoons olive oil",
                "2 cloves garlic, minced",
                "1 teaspoon ground cumin",
                "1 teaspoon chili powder",
                "1 teaspoon smoked paprika",
                "Salt and black pepper to taste",
                "2 tablespoons chipotle peppers in adobo sauce, minced",
                "Flour tortillas",
                "Cooked white or brown rice",
                "Black beans, drained and rinsed",
                "Corn kernels",
                "Diced tomatoes",
                "Shredded lettuce",
                "Grated cheddar or Monterey Jack cheese",
                "Sour cream",
                "Guacamole or sliced avocados",
                "Fresh cilantro, chopped",
                "Lime wedges"
            ),
            instructions = listOf(
                "In a bowl, mix minced garlic, cumin, chili powder, smoked paprika, salt, black pepper, and minced chipotle peppers to create a marinade.",
                "Rub the chicken strips with the marinade and let marinate for up to 30 minutes.",
                "Heat olive oil in a skillet over medium-high heat. Cook marinated chicken strips until cooked and slightly charred, about 5-7 minutes per side.",
                "Assemble the burritos: Place a flour tortilla on a clean surface. Layer rice, black beans, corn, diced tomatoes, shredded lettuce, chipotle chicken, cheese, sour cream, guacamole, and cilantro.",
                "Fold in the sides and roll tightly. Optionally grill on a skillet for a crispy exterior. Serve with lime wedges."
            ),
            suggestedSides = listOf("Tortilla Chips & Queso", "Mexican Street Corn", "Chips & Salsa")
        ),
        RecipeEntity(
            title = "Roast Beef Tenderloin",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 60,
            ingredients = listOf(
                "4 to 5 lb Beef tenderloin, trimmed and tied",
                "1 tsp salt",
                "1/2 tsp black pepper",
                "2 tbsp olive oil",
                "4 tbsp butter, salted, softened",
                "2 tsp garlic, minced",
                "1 tsp horseradish, prepared or dijon mustard",
                "1 tsp rosemary, minced or thyme"
            ),
            instructions = listOf(
                "Tie up tenderloin and season with salt and pepper. Let come to room temperature before cooking.",
                "Preheat oven to 425°F.",
                "Place oil in a large cast-iron skillet over medium-high heat. Sear the beef tenderloin on all sides, about 3 to 5 minutes per side.",
                "Combine softened butter, garlic, horseradish, and herbs in a small dish.",
                "Slather the seared tenderloin with the butter mixture and roast in preheated oven for 20 to 25 minutes until desired internal temp (120-125°F for medium-rare).",
                "Remove from oven and rest for 15 to 20 minutes before slicing into 1-inch slices."
            ),
            suggestedSides = listOf("Mashed Potatoes", "Roasted Asparagus", "Yorkshire Pudding", "Caesar Salad")
        ),
        RecipeEntity(
            title = "Chilli",
            protein = ProteinType.BEEF,
            prepAndCookMinutes = 40,
            ingredients = listOf(
                "Can of V8",
                "2 cans Brooks chili beans",
                "1 lb ground beef",
                "1 diced onion",
                "2 cloves garlic",
                "2 diced celery stalks",
                "chilli powder",
                "1 tbsp vegetable oil"
            ),
            instructions = listOf(
                "Heat oil in large pot, add onions and sauté until clear.",
                "Add garlic, sauté for 1 minute.",
                "Add V8, celery, beans, and chilli powder.",
                "Brown meat and add to chili.",
                "Simmer until flavors meld and celery is tender."
            ),
            suggestedSides = listOf("Cornbread", "Saltine Crackers", "Fritos", "Shredded Cheese & Sour Cream")
        ),
        RecipeEntity(
            title = "Mushroom Risotto",
            protein = ProteinType.VEGETARIAN,
            prepAndCookMinutes = 45,
            ingredients = listOf(
                "6 cups chicken broth, divided",
                "3 tablespoons olive oil, divided",
                "1 pound portobello mushrooms, thinly sliced",
                "1 pound white mushrooms, thinly sliced",
                "2 shallots, diced",
                "1 1/2 cups Arborio rice",
                "1/2 cup dry white wine",
                "sea salt",
                "freshly ground black pepper",
                "3 tablespoons finely chopped chives",
                "4 tablespoons butter",
                "1/3 cup freshly grated Parmesan cheese"
            ),
            instructions = listOf(
                "In a saucepan, warm the broth over low heat. Warm 2 tablespoons olive oil in a large saucepan over medium-high heat. Stir in mushrooms and cook until soft, about 3 minutes. Remove mushrooms and their liquid, and set aside.",
                "Add 1 tablespoon olive oil to skillet, and stir in shallots. Cook 1 minute.",
                "Add Arborio rice, stirring to coat with oil, about 2 minutes. Pour in wine, stirring constantly until absorbed.",
                "Add 1/2 cup warm broth at a time, stirring continuously until absorbed and rice is al dente, about 25 to 35 minutes.",
                "Remove from heat, and stir in mushrooms with their liquid, butter, chives, and Parmesan. Season with salt and pepper."
            ),
            suggestedSides = listOf("Arugula Salad", "Garlic Bread", "Roasted Brussels Sprouts")
        )
    )
}
