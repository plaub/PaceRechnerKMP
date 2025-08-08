package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.*

/**
 * Settings repository with persistent storage.
 * Uses platform-specific storage implementations.
 */
object SettingsRepository {
    private const val KEY_DEFAULT_DISTANCE = "default_distance"

    private var storage: SettingsStorage? = null
    private var _defaultDistance = mutableStateOf("ld")
    val defaultDistance: State<String> = _defaultDistance

    fun initialize(settingsStorage: SettingsStorage) {
        storage = settingsStorage
        // Load saved value or use default
        _defaultDistance.value = storage!!.getString(KEY_DEFAULT_DISTANCE, "ld")
    }

    fun setDefaultDistance(distance: String) {
        _defaultDistance.value = distance
        storage?.putString(KEY_DEFAULT_DISTANCE, distance)
    }

    fun getDefaultDistance(): String {
        return _defaultDistance.value
    }
}
