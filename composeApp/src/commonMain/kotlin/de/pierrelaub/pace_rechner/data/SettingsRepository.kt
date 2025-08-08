package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.*

/**
 * Settings repository with persistent storage.
 * Uses platform-specific storage implementations.
 */
object SettingsRepository {
    private const val KEY_DEFAULT_DISTANCE = "default_distance"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"

    private var storage: SettingsStorage? = null
    private var _defaultDistance = mutableStateOf("ld")
    val defaultDistance: State<String> = _defaultDistance

    private var _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    fun initialize(settingsStorage: SettingsStorage) {
        storage = settingsStorage
        // Load saved values or use defaults
        _defaultDistance.value = storage!!.getString(KEY_DEFAULT_DISTANCE, "ld")
        _isDarkTheme.value = storage!!.getBoolean(KEY_IS_DARK_THEME, false)
    }

    fun setDefaultDistance(distance: String) {
        _defaultDistance.value = distance
        storage?.putString(KEY_DEFAULT_DISTANCE, distance)
    }

    fun getDefaultDistance(): String {
        return _defaultDistance.value
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        storage?.putBoolean(KEY_IS_DARK_THEME, isDark)
    }

    fun isDarkTheme(): Boolean {
        return _isDarkTheme.value
    }
}
