package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofileapp.data.ProfileUiState
import com.example.myprofileapp.data.local.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsManager.themeFlow.collect { theme ->
                _uiState.update { it.copy(isDarkMode = theme == "Dark") }
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val newTheme = if (_uiState.value.isDarkMode) "Light" else "Dark"
            settingsManager.setTheme(newTheme)
        }
    }

    fun openEditMode() {
        _uiState.update { it.copy(isEditMode = true) }
    }

    fun closeEditMode() {
        _uiState.update { it.copy(isEditMode = false) }
    }

    fun saveProfile(newName: String, newBio: String) {
        _uiState.update {
            it.copy(
                name = newName.trim(),
                bio = newBio.trim(),
                isEditMode = false
            )
        }
    }
}
