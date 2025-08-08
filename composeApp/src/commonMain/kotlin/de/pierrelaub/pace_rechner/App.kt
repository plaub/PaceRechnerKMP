package de.pierrelaub.pace_rechner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import de.pierrelaub.pace_rechner.data.InitializeSettings
import de.pierrelaub.pace_rechner.ui.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    // Initialize settings storage for the current platform
    InitializeSettings()

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            MainNavigation(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}