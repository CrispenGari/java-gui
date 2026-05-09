# Build Verification Note

The project was prepared as a complete Android Studio project using Java, XML layouts, Room Database, and Material Design Components.

In this ChatGPT sandbox, Gradle could not complete `./gradlew assembleDebug` because the Gradle wrapper attempted to download Gradle 8.11.1 from `services.gradle.org`, but outbound network access is blocked in the sandbox environment. Open the project in Android Studio with internet access enabled and allow Gradle sync to download the wrapper distribution and dependencies.

Recommended local verification steps:

```bash
./gradlew clean assembleDebug
```

Then test the app with:

```text
Officer ID: admin
PIN: 1234
Approved student example: 202232773
Denied student example: 123456789
```
