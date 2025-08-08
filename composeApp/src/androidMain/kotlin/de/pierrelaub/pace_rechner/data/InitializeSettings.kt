package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun InitializeSettings() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val storage = createSettingsStorage(context)
        SettingsRepository.initialize(storage)
    }
}
