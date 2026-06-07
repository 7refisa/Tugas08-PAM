package com.example.myprofileapp.data.local

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

actual fun createSettings(): ObservableSettings {
    val context = DatabaseDriverFactory.appContext ?: throw IllegalStateException("Android Context not initialized. Set DatabaseDriverFactory.appContext in MainActivity.")
    val sharedPrefs = context.getSharedPreferences("notes_settings", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sharedPrefs)
}
