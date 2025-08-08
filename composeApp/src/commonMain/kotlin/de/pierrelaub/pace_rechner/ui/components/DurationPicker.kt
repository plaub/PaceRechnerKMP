package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.pierrelaub.pace_rechner.util.secondsToHHMMSS
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DurationPicker(
    totalSeconds: Int?,
    onTotalSecondsChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false
) {
    var selectedHour by remember { mutableStateOf("00") }
    var selectedMinute by remember { mutableStateOf("01") }
    var selectedSecond by remember { mutableStateOf("30") }

    var hoursExpanded by remember { mutableStateOf(false) }
    var minutesExpanded by remember { mutableStateOf(false) }
    var secondsExpanded by remember { mutableStateOf(false) }

    val hours = (0..23).map { it.toString().padStart(2, '0') }
    val minutesAndSeconds = (0..59).map { it.toString().padStart(2, '0') }

    fun updateParent() {
        val h = selectedHour.toIntOrNull() ?: 0
        val m = selectedMinute.toIntOrNull() ?: 0
        val s = selectedSecond.toIntOrNull() ?: 0
        onTotalSecondsChange(h * 3600 + m * 60 + s)
    }

    LaunchedEffect(totalSeconds) {
        if (totalSeconds != null && totalSeconds >= 0) {
            val parts = secondsToHHMMSS(totalSeconds, false).split(":")
            if (parts.size == 3) {
                selectedHour = parts[0]
                selectedMinute = parts[1]
                selectedSecond = parts[2]
            }
        } else if (totalSeconds == null) {
            selectedHour = "00"
            selectedMinute = "01"
            selectedSecond = "30"
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hours
        TimePickerDropdown(
            value = selectedHour,
            options = hours,
            expanded = hoursExpanded,
            onExpandedChange = { hoursExpanded = it },
            onValueChange = {
                selectedHour = it
                updateParent()
            },
            label = "HH",
            disabled = disabled
        )

        Text(
            text = ":",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Minutes
        TimePickerDropdown(
            value = selectedMinute,
            options = minutesAndSeconds,
            expanded = minutesExpanded,
            onExpandedChange = { minutesExpanded = it },
            onValueChange = {
                selectedMinute = it
                updateParent()
            },
            label = "MM",
            disabled = disabled
        )

        Text(
            text = ":",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Seconds
        TimePickerDropdown(
            value = selectedSecond,
            options = minutesAndSeconds,
            expanded = secondsExpanded,
            onExpandedChange = { secondsExpanded = it },
            onValueChange = {
                selectedSecond = it
                updateParent()
            },
            label = "SS",
            disabled = disabled
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDropdown(
    value: String,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    label: String,
    disabled: Boolean
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (!disabled) onExpandedChange(it) }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = !disabled,
            // Icon komplett entfernt für bessere Lesbarkeit
            trailingIcon = null,
            modifier = Modifier
                .menuAnchor()
                .width(56.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
            )
        )

        if (!disabled) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            onValueChange(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DurationPickerPreview() {
    DurationPicker(
        totalSeconds = 3690, // 1:01:30
        onTotalSecondsChange = {}
    )
}

@Preview
@Composable
private fun DurationPickerDisabledPreview() {
    MaterialTheme {
        DurationPicker(totalSeconds = 3661, onTotalSecondsChange = {}, disabled = true)
    }
}

@Preview
@Composable
private fun DurationPickerNullPreview() {
    MaterialTheme {
        DurationPicker(totalSeconds = null, onTotalSecondsChange = {})
    }
}