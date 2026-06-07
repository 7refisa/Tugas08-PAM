package com.example.myprofileapp.data.local

import android.os.Build

actual class DeviceInfo actual constructor() {
    actual fun getDeviceName(): String = Build.MODEL
    actual fun getOsVersion(): String = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    actual fun getAppVersion(): String = "1.0.0"
}
