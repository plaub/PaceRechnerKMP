package de.pierrelaub.pace_rechner.ui.navigation.voyager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

// Shared ViewModel für alle Tabs - einfache Implementierung ohne Voyager ScreenModel
@Composable
fun rememberSharedViewModel(): PaceRechnerViewModel {
    return remember { PaceRechnerViewModel() }
}
