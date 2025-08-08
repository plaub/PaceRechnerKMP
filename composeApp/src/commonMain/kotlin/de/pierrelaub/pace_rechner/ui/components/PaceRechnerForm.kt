package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.pierrelaub.pace_rechner.types.DistanceUnit
import de.pierrelaub.pace_rechner.types.PaceType
import de.pierrelaub.pace_rechner.types.PaceUnit
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@Composable
fun PaceRechnerForm(
    title: String = "Run",
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    paceUnit: PaceUnit = PaceUnit.Run,
    distance: Double = 1.0,
    onDistanceChange: (Double) -> Unit = {},
    time: Int = 1,
    onTimeChange: (Int) -> Unit = {},
    pace: Int = 1,
    onPaceChange: (Int) -> Unit = {},
    speed: Double = 1.0,
    onSpeedChange: (Double) -> Unit = {},
    paceType: PaceType = PaceType.Pace,
    distanceUnit: DistanceUnit = DistanceUnit.Run,
    modifier: Modifier = Modifier
) {
    var distanceText by remember { mutableStateOf(distance.toString()) }
    var speedText by remember { mutableStateOf(speed.toString()) }

    // Update local text states when props change
    LaunchedEffect(distance) {
        distanceText = distance.toString()
    }

    LaunchedEffect(speed) {
        speedText = speed.toString()
    }

    // Initialize calculations on mount
    LaunchedEffect(Unit) {
        if (paceType == PaceType.Pace) {
            handlePaceChange(pace, distance, paceUnit, onTimeChange, onPaceChange)
        } else {
            handleSpeedChange(speed, distance, onTimeChange, onSpeedChange)
        }
    }

    Column(
        modifier = modifier
            .background(backgroundColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Distance Row
        FormRow(
            label = "Distanz",
            unit = distanceUnit.value,
            textColor = textColor
        ) {
            StyledTextField(
                value = distanceText,
                onValueChange = { distanceText = it },
                onDone = {
                    handleDistanceChange(
                        distanceText,
                        paceType,
                        pace,
                        speed,
                        paceUnit,
                        onDistanceChange,
                        onTimeChange
                    )
                },
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }

        // Time Row
        FormRow(
            label = "Zeit",
            unit = null,
            textColor = textColor
        ) {
            DurationPicker(
                totalSeconds = time,
                onTotalSecondsChange = { newTime ->
                    handleTimeChange(
                        newTime,
                        paceType,
                        distance,
                        paceUnit,
                        onTimeChange,
                        onPaceChange,
                        onSpeedChange
                    )
                }
            )
        }

        // Pace Row (only if paceType is Pace)
        if (paceType == PaceType.Pace) {
            FormRow(
                label = "Pace",
                unit = paceUnit.value,
                textColor = textColor
            ) {
                DurationPicker(
                    totalSeconds = pace,
                    onTotalSecondsChange = { newPace ->
                        handlePaceChange(newPace, distance, paceUnit, onTimeChange, onPaceChange)
                    }
                )
            }
        }

        // Speed Row (only if paceType is Speed)
        if (paceType == PaceType.Speed) {
            FormRow(
                label = "Speed",
                unit = "km/h",
                textColor = textColor
            ) {
                StyledTextField(
                    value = speedText,
                    onValueChange = { speedText = it },
                    onDone = {
                        handleSpeedChange(
                            speedText.toDoubleOrNull() ?: speed,
                            distance,
                            onTimeChange,
                            onSpeedChange
                        )
                    },
                    backgroundColor = backgroundColor,
                    textColor = textColor
                )
            }
        }
    }
}

@Composable
private fun FormRow(
    label: String,
    unit: String?,
    textColor: Color,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = label,
                color = textColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            if (unit != null) {
                Text(
                    text = unit,
                    color = textColor.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        content()
    }
}

@Composable
private fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        singleLine = true,
        modifier = modifier.width(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFF2D3436),
            unfocusedTextColor = Color(0xFF2D3436),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.White.copy(alpha = 0.8f),
            unfocusedBorderColor = Color.White.copy(alpha = 0.6f)
        ),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    )
}

// Helper functions remain the same
private fun handleDistanceChange(
    distanceText: String,
    paceType: PaceType,
    pace: Int,
    speed: Double,
    paceUnit: PaceUnit,
    onDistanceChange: (Double) -> Unit,
    onTimeChange: (Int) -> Unit
) {
    val distance = distanceText.replace(",", ".").toDoubleOrNull()
    if (distance != null && distance >= 10) {
        if (paceType == PaceType.Pace) {
            val newTime = if (paceUnit == PaceUnit.Run) {
                round((pace * distance) / 1000).toInt()
            } else {
                round((pace * distance) / 100).toInt()
            }
            onTimeChange(newTime)
        } else {
            val newTime = round((distance / speed) * 3600).toInt()
            onTimeChange(newTime)
        }
        onDistanceChange(distance)
    }
}

private fun handlePaceChange(
    newPace: Int,
    distance: Double,
    paceUnit: PaceUnit,
    onTimeChange: (Int) -> Unit,
    onPaceChange: (Int) -> Unit
) {
    val newTime = if (paceUnit == PaceUnit.Run) {
        round((newPace * distance) / 1000).toInt()
    } else {
        round((newPace * distance) / 100).toInt()
    }
    onTimeChange(newTime)
    onPaceChange(newPace)
}

private fun handleTimeChange(
    newTime: Int,
    paceType: PaceType,
    distance: Double,
    paceUnit: PaceUnit,
    onTimeChange: (Int) -> Unit,
    onPaceChange: (Int) -> Unit,
    onSpeedChange: (Double) -> Unit
) {
    if (paceType == PaceType.Pace) {
        val newPace = if (paceUnit == PaceUnit.Run) {
            round((newTime * 1000) / distance).toInt()
        } else {
            round((newTime * 100) / distance).toInt()
        }
        onPaceChange(newPace)
    } else {
        val h = newTime / 3600.0
        val newSpeed = round((distance / h) * 10) / 10.0
        onSpeedChange(newSpeed)
    }
    onTimeChange(newTime)
}

private fun handleSpeedChange(
    newSpeed: Double,
    distance: Double,
    onTimeChange: (Int) -> Unit,
    onSpeedChange: (Double) -> Unit
) {
    if (!newSpeed.isNaN()) {
        val newTime = round((distance / newSpeed) * 3600).toInt()
        onTimeChange(newTime)
        onSpeedChange(newSpeed)
    }
}

@Preview
@Composable
private fun PaceRechnerFormPreview() {
    PaceRechnerForm()
}
