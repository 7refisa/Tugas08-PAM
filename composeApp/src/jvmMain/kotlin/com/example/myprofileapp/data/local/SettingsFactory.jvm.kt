package com.example.myprofileapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual fun createSettings(): ObservableSettings {
    val prefs = Preferences.userRoot().node("com.example.myprofileapp.settings")
    return PreferencesSettings(prefs)
}
