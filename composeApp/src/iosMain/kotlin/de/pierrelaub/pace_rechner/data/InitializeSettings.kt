package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun InitializeSettings() {
    LaunchedEffect(Unit) {
        val storage = createSettingsStorage()
        SettingsRepository.initialize(storage)
    }
}
