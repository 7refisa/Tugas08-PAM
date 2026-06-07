package com.example.myprofileapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import platform.Foundation.NSUserDefaults

actual fun createSettings(): ObservableSettings {
    return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
}
