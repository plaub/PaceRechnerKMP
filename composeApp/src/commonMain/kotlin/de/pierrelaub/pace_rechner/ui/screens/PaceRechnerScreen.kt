package de.pierrelaub.pace_rechner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.pierrelaub.pace_rechner.ui.components.PaceRechner
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PaceRechnerScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PaceRechner(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun PaceRechnerScreenPreview() {
    MaterialTheme {
        PaceRechnerScreen()
    }
}
