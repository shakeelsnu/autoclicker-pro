# Auto Clicker Pro - Native Android App

A production-grade, native Android gesture automation and screen recording application built with Kotlin 1.9+ and Jetpack Compose (Minimum SDK 26, Target SDK 34).

Auto Clicker Pro records user touch actions and screen input system-wide, saves them into indexed profiles (P1, P2, P3...), and replays them manually, instantly via drawn ML Kit gestures, or via automated alarms.

## Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK Platform 34

## Build & Run
```bash
cd android
./gradlew assembleDebug
```
The output APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Key System Components
1. AccessibilityService: Dispatches gestures via dispatchGesture() and captures node resource IDs.
2. SYSTEM_ALERT_WINDOW: Floating overlay with auto-evasion that flips sides when a touch is scheduled beneath it.
3. Custom IME Keyboard: Injects Tab, Esc, Enter, F1-F12, and Numpad keys system-wide.
4. AlarmManager: Uses setExactAndAllowWhileIdle for exact scheduling with screen-off wake lock and locked guardrails.
5. ML Kit Digital Ink Recognition: On-device handwriting recognition for drawing codes (P1, P2).