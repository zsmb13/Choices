# Choices

Choices is a Kotlin Multiplatform application built with Compose Multiplatform, designed to help you
track your decisions and maintain a personal score.

## Features

- **Track Score:** Quickly add positive (+) or negative (-) records to your daily score.
- **Add Context:** Attach optional comments to each entry to remember why you made that choice.
- **History:** View a comprehensive list of all your past records.
- **Details:** Inspect the specific details (score, comment, timestamp) of any record.
- **Multiplatform Support:** Run the same experience on Android, iOS, and Desktop (JVM).

## Tech Stack

This project demonstrates a modern Kotlin Multiplatform architecture leveraging the following
libraries:

- **UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Material 3)
- **Navigation:** [AndroidX Navigation 3](https://developer.android.com/jetpack/androidx/releases/navigation3) (Multiplatform support)
- **Database:** [Room](https://developer.android.com/training/data-storage/room) (SQLite)
- **Dependency Injection:** [Metro](https://github.com/ZacSweers/metro)
- **Date & Time:** [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime)
- **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

## Project Structure

* `shared`: Contains the core logic and UI code shared across all platforms.
    * `commonMain`: Code common to all targets.
    * `androidMain`, `iosMain`, `jvmMain`: Platform-specific implementations.
* `androidApp`: Android application entry point and configuration.
* `iosApp`: iOS application entry point and configuration.
* `desktopApp`: Desktop application entry point and configuration.

## Building and Running

### Android

To run the Android app, use the `androidApp` run configuration in Android Studio or run:

```bash
./gradlew :androidApp:assembleDebug
```

### Desktop (JVM)

To run the Desktop app, use the `desktopApp` run configuration in IntelliJ IDEA or run:

```bash
./gradlew :desktopApp:run
```

### iOS

To run the iOS app, open `iosApp/iosApp.xcodeproj` in Xcode and run it from there.