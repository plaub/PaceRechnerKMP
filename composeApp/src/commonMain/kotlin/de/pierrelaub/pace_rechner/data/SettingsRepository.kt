package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.*
import de.pierrelaub.pace_rechner.types.CompetitionType

/**
 * Settings repository with persistent storage.
 * Uses platform-specific storage implementations.
 */
object SettingsRepository {
    private const val KEY_DEFAULT_DISTANCE = "default_distance"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"
    private const val KEY_COMPETITION_TYPE = "competition_type"

    private var storage: SettingsStorage? = null
    private var _defaultDistance = mutableStateOf("ld")
    val defaultDistance: State<String> = _defaultDistance

    private var _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    private var _competitionType = mutableStateOf(CompetitionType.Triathlon)
    val competitionType: State<CompetitionType> = _competitionType

    fun initialize(settingsStorage: SettingsStorage) {
        storage = settingsStorage
        // Load saved values or use defaults
        _defaultDistance.value = storage!!.getString(KEY_DEFAULT_DISTANCE, "ld")
        _isDarkTheme.value = storage!!.getBoolean(KEY_IS_DARK_THEME, false)

        // Load competition type
        val competitionTypeValue = storage!!.getString(KEY_COMPETITION_TYPE, CompetitionType.Triathlon.value)
        _competitionType.value = CompetitionType.entries.find { it.value == competitionTypeValue }
            ?: CompetitionType.Triathlon
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

    fun setCompetitionType(competitionType: CompetitionType) {
        _competitionType.value = competitionType
        storage?.putString(KEY_COMPETITION_TYPE, competitionType.value)
    }

    fun getCompetitionType(): CompetitionType {
        return _competitionType.value
    }
}
