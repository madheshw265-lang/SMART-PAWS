# SMART PAWS Android

Firebase-connected Android companion app for the SMART PAWS GPS dog-belt project.

Package: `com.madhesh.smartpaws`

Database path: `/smartPaws/device01`

Features:
- Firebase email/password login
- Live Realtime Database dashboard
- SAFE / OUTSIDE status
- distance, GPS coordinates, home location and satellites
- open dog location in a map app
- Android 13+ notification permission
- foreground monitoring service
- local notifications on SAFE -> OUTSIDE and OUTSIDE -> SAFE transitions
- FCM receiver included for future server-sent messages

## Build
Open this folder in a current Android Studio, allow Gradle sync, then use:
Build > Build Bundle(s) / APK(s) > Build APK(s)

Debug APK path:
`app/build/outputs/apk/debug/app-debug.apk`

## Notification architecture
For the free/demo setup, the app monitors Realtime Database from a foreground service and posts local Android notifications. The FCM receiver is included, but FCM requires a server-side sender (for example a Firebase Cloud Function) to push messages remotely.
