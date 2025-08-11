package de.pierrelaub.pace_rechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import de.pierrelaub.pace_rechner.data.SettingsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppWithSystemUI()
        }
    }
}

@Composable
fun AppWithSystemUI() {
    // Get theme preference from settings
    val isDarkTheme by SettingsRepository.isDarkTheme

    // Set system UI colors based on theme using LaunchedEffect instead of SideEffect
    val view = LocalView.current
    LaunchedEffect(isDarkTheme) {
        if (!view.isInEditMode) {
            val window = (view.context as ComponentActivity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            // Ensure content draws behind system bars
            WindowCompat.setDecorFitsSystemWindows(window, false)

            if (isDarkTheme) {
                // System bars are transparent for edge-to-edge.
                // Set icon colors for dark theme
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            } else {
                // System bars are transparent for edge-to-edge.
                // Set icon colors for light theme
                insetsController.isAppearanceLightStatusBars = true
                insetsController.isAppearanceLightNavigationBars = true
            }
        }
    }

    // Use Surface to ensure proper background color handling
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF8F9FA)
    ) {
        App()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppWithSystemUI()
}