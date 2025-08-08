package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PaceRechnerTransition(
    time: Int = 60, // Default 1 minute
    onTimeChange: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFFDDA0DD))
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Transition",
            color = Color(0xFF2D3436),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        DurationPicker(
            totalSeconds = time,
            onTotalSecondsChange = onTimeChange
        )
    }
}

@Preview
@Composable
private fun PaceRechnerTransitionPreview() {
    MaterialTheme {
        PaceRechnerTransition(
            time = 120
        )
    }
}
