package com.example.myprofileapp.data.local

actual class DeviceInfo actual constructor() {
    actual fun getDeviceName(): String = System.getProperty("os.name") ?: "Desktop JVM"
    actual fun getOsVersion(): String = System.getProperty("os.version") ?: "Unknown"
    actual fun getAppVersion(): String = "1.0.0"
}
