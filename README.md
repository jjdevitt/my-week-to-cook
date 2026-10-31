# My Week to Cook
My app for when it's my week to cook!

## Process

### Planning
For planning I need to be able to look at, and add recipies for the week to a 'dinner list plan'

First, for a given we I decide how many dinners I'm making (usually between 3 and 5).

Then I note if I'm going to be limited on cook or preperation time (some days I get home late from work and have less time to cook and will need to select main course recipies accordingly) - so the amount of time a recipe takes can be important.

For main course recipies .... I pick some number of recipies from my existing 'collection' of recipies. I also may research 1 or 2 new recipies - if these turn out good they get added to the list permanently, if not they get discarded.  I try not to use the same protein more than once... twice at most.  

After picking dinners I review them to make sure each also inclues one or two sides.

After all dinners are planned the ingredients need to go onto my shopping list

## Shopping
When I go to the store I need a view that shows me a shopping list.

In addition to the items for this weeks dinners my shopping list should also includes a few things I need to buy every week (apples, bananas, milk, eggs, cheese).  Generally I do not add things like salt, oil, and common spices to my grocery list because I usually have these.  List should be reorderable, and when I check an item off it should remain visible but go to the bottom.

## Cooking
When I select a dinner from the list I should see a list of ingredients (maybe it can be togglable since I won't need to keep looking at it while cooking) and a set of instructions.  

## Building Locally

### Prerequisites
- **JDK 17** (required for Android Gradle Plugin 8.5+ and Kotlin 2.0)
- **Android Studio** (Koala / Ladybug or newer recommended) with **Android SDK Platform 34** installed
- An Android device (API 26 / Android 8.0+) or an Android Emulator

### Option 1: Build & Run in Android Studio (Recommended)
1. Open **Android Studio** and select **Open**.
2. Choose this repository folder (`/Users/jamardevitt/dev/my-week-to-cook`).
3. Wait for Android Studio to automatically generate `local.properties` and complete the initial **Gradle Sync**.
4. Select a connected Android device or emulator from the top device dropdown and click **Run (`▶`)** to install and launch the `app` configuration.

### Option 2: Build from the Command Line
1. Ensure your Android SDK location is set via `ANDROID_HOME` (or in `local.properties` at the project root):
   ```properties
   sdk.dir=/Users/<your-username>/Library/Android/sdk
   ```
2. If you do not yet have the `./gradlew` script generated, either open the project once in Android Studio or generate the wrapper with a local Gradle installation:
   ```bash
   gradle wrapper
   ```
3. Build the debug APK:
   ```bash
   ./gradlew assembleDebug
   ```
   The compiled APK will be output to `app/build/outputs/apk/debug/app-debug.apk`.
4. Install directly onto a connected device or running emulator:
   ```bash
   ./gradlew installDebug
   ```
5. Run the unit tests:
   ```bash
   ./gradlew testDebugUnitTest
   ```
