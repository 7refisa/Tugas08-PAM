package com.example.myprofileapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class SettingsManager(settings: ObservableSettings = createSettings()) {
    private val flowSettings = settings.toFlowSettings()

    companion object {
        const val KEY_THEME = "app_theme"
        const val KEY_SORT_ORDER = "notes_sort_order"
        
        // Sort options
        const val SORT_NEWEST = "newest"
        const val SORT_OLDEST = "oldest"
    }

    val themeFlow: Flow<String> = flowSettings.getStringFlow(KEY_THEME, "System")
    val sortOrderFlow: Flow<String> = flowSettings.getStringFlow(KEY_SORT_ORDER, SORT_NEWEST)

    suspend fun setTheme(theme: String) {
        flowSettings.putString(KEY_THEME, theme)
    }

    suspend fun setSortOrder(sortOrder: String) {
        flowSettings.putString(KEY_SORT_ORDER, sortOrder)
    }
}
