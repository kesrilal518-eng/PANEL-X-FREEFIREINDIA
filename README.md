# PANEL X FREEFIREINDIA

**Unofficial sensitivity & gaming training guide for Free Fire.**

This application is a **visual gaming panel and sensitivity guide only**.

## Important Safety Notice

- All **AIMBOT**, **AUTO HEADSHOT**, **TOUCH AUTOMATION**, **MACROS**, **INJECTOR**, and **GAME ACCESS** features are **visual demo UI only**.
- They do **not** interact with, modify, automate, inject into, or control Free Fire or any other game.
- No game memory access, code injection, overlays that control games, automated touch input, or real macros are implemented.

When you tap these buttons you will see a message such as:

> DEMO MODE — This feature is a visual simulation only.

## Features

- Animated splash screen
- Automatic device detection (manufacturer, model, Android version, resolution, density, refresh rate, RAM)
- Sensitivity recommendation system (Low / Balanced / High / Custom)
- DPI & fire button educational guide
- Headshot drag training with in-app visual animation
- Gaming panel with demo-only toggles
- Saved sensitivity profiles (Room database)
- Share settings via Android share sheet
- Bottom navigation (Home, Sensitivity, Training, Panel, Settings)
- Dark gaming-style Material 3 UI
- Hindi / English language preference
- Full disclaimer screen

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Navigation Compose
- Room
- DataStore
- Coroutines / Flow

## Build

Open the project in Android Studio (Hedgehog or newer recommended).

```bash
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/`

## GitHub Actions

On push to `main` (or manual `workflow_dispatch`) the workflow builds the debug APK and uploads it as artifact:

`panel-x-freefireindia-debug-apk`

## Disclaimer

PANEL X FREEFIREINDIA is an unofficial sensitivity and gaming training guide.  
This application does not modify, inject into, automate, or control any game.  
The gaming panel features marked DEMO MODE are visual simulations only.  
Not affiliated with Garena or Free Fire.
