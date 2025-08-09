package de.pierrelaub.pace_rechner.resources

import androidx.compose.runtime.*

enum class Language(val code: String, val displayName: String) {
    GERMAN("de", "Deutsch"),
    ENGLISH("en", "English")
}

object LocalizationManager {
    private val _currentLanguage = mutableStateOf(Language.GERMAN)
    val currentLanguage: State<Language> = _currentLanguage

    val strings: Strings
        @Composable
        get() = when (currentLanguage.value) {
            Language.GERMAN -> StringsGerman
            Language.ENGLISH -> StringsEnglish
        }

    fun setLanguage(language: Language) {
        _currentLanguage.value = language
    }
}

@Composable
fun LocalizedStrings(): Strings {
    return LocalizationManager.strings
}
