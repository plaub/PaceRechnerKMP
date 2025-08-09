package de.pierrelaub.pace_rechner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.pierrelaub.pace_rechner.ui.components.DynamicPaceRechner
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PaceRechnerScreen(
    modifier: Modifier = Modifier,
    viewModel: PaceRechnerViewModel? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (viewModel != null) {
            DynamicPaceRechner(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            )
        } else {
            DynamicPaceRechner(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PaceRechnerScreenPreview() {
    MaterialTheme {
        PaceRechnerScreen()
    }
}
