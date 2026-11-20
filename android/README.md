# Muntaha Tasbih Android APK

This Android wrapper opens the existing live application at:

https://muntaha-tasbih.vercel.app/

It does not create a second database or copy user records. Sign in with the same Firebase account to access the same cloud data. Android WebView keeps its own login session separately from Chrome.

## Build

The GitHub Actions workflow **Build Android APK** builds a debug APK on pushes to the `android-apk` branch or when manually started.

Download the `muntaha-tasbih-android-apk` artifact from the completed workflow run. The APK is currently a debug build for installation/testing, not a Play Store release.

Application ID: `com.muntaha.tasbih`
Minimum Android: Android 6.0 (API 23)
