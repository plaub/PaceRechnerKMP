package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import de.pierrelaub.pace_rechner.data.SettingsRepository
import de.pierrelaub.pace_rechner.types.DistanceUnit
import de.pierrelaub.pace_rechner.types.PaceType
import de.pierrelaub.pace_rechner.types.PaceUnit
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel
import de.pierrelaub.pace_rechner.util.secondsToHHMMSS
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaceRechner(
    modifier: Modifier = Modifier,
    viewModel: PaceRechnerViewModel = remember { PaceRechnerViewModel() }
) {
    val presetOptions = listOf(
        "" to "Vorlagen",
        "sprint" to "Sprint",
        "olympic" to "Olympisch",
        "md" to "Mitteldistanz",
        "ld" to "Langdistanz"
    )

    // Use LazyColumn to fix the scrolling issue
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toolbar section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ExposedDropdownMenuBox(
                        expanded = viewModel.presetExpanded,
                        onExpandedChange = { viewModel.updatePresetExpanded(it) }
                    ) {
                        OutlinedTextField(
                            value = presetOptions.find { it.first == viewModel.preset }?.second ?: "Vorlagen",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.presetExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .width(150.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = viewModel.presetExpanded,
                            onDismissRequest = { viewModel.updatePresetExpanded(false) }
                        ) {
                            presetOptions.forEach { (value, label) ->
                                DropdownMenuItem(
                                    text = { Text(label) },
                                    onClick = {
                                        viewModel.updatePreset(value)
                                        viewModel.updatePresetExpanded(false)
                                        if (value.isNotEmpty()) {
                                            viewModel.handlePresetChange(value)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // Save Button
                    var showSaveDialog by remember { mutableStateOf(false) }
                    var saveName by remember { mutableStateOf("") }

                    Button(
                        onClick = {
                            saveName = ""
                            showSaveDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("💾 Speichern")
                    }

                    // Save Dialog
                    if (showSaveDialog) {
                        AlertDialog(
                            onDismissRequest = { showSaveDialog = false },
                            title = { Text("Berechnung speichern") },
                            text = {
                                OutlinedTextField(
                                    value = saveName,
                                    onValueChange = { saveName = it },
                                    label = { Text("Name eingeben") },
                                    placeholder = { Text("z.B. Ironman Training") },
                                    singleLine = true
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        if (saveName.isNotBlank()) {
                                            de.pierrelaub.pace_rechner.data.HistoryRepository.saveCalculation(
                                                name = saveName,
                                                swimDistance = viewModel.swimDistance,
                                                swimTime = viewModel.swimTime,
                                                swimPace = viewModel.swimPace,
                                                bikeDistance = viewModel.bikeDistance,
                                                bikeTime = viewModel.bikeTime,
                                                bikeSpeed = viewModel.bikeSpeed,
                                                runDistance = viewModel.runDistance,
                                                runTime = viewModel.runTime,
                                                runPace = viewModel.runPace,
                                                t1Time = viewModel.t1Time,
                                                t2Time = viewModel.t2Time,
                                                dayTimeStart = viewModel.dayTimeStart,
                                                presetType = viewModel.preset
                                            )
                                            showSaveDialog = false
                                        }
                                    },
                                    enabled = saveName.isNotBlank()
                                ) {
                                    Text("Speichern")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showSaveDialog = false }) {
                                    Text("Abbrechen")
                                }
                            }
                        )
                    }
                }
            }
        }

        // Swim
        item {
            PaceRechnerCard(
                backgroundColor = de.pierrelaub.pace_rechner.ui.theme.SwimColor,
                textColor = de.pierrelaub.pace_rechner.ui.theme.SwimOnColor,
                title = "Swim"
            ) {
                PaceRechnerForm(
                    title = "Swim",
                    backgroundColor = de.pierrelaub.pace_rechner.ui.theme.SwimColor,
                    textColor = de.pierrelaub.pace_rechner.ui.theme.SwimOnColor,
                    distance = viewModel.swimDistance,
                    onDistanceChange = { viewModel.updateSwimDistance(it) },
                    time = viewModel.swimTime,
                    onTimeChange = { viewModel.updateSwimTime(it) },
                    pace = viewModel.swimPace,
                    onPaceChange = { viewModel.updateSwimPace(it) },
                    paceType = PaceType.Pace,
                    paceUnit = PaceUnit.Swim,
                    distanceUnit = DistanceUnit.Swim
                )
            }
        }

        // T1
        item {
            PaceRechnerCard(
                backgroundColor = de.pierrelaub.pace_rechner.ui.theme.TransitionColor,
                textColor = de.pierrelaub.pace_rechner.ui.theme.TransitionOnColor,
                title = "T1"
            ) {
                PaceRechnerTransition(
                    time = viewModel.t1Time,
                    onTimeChange = { viewModel.updateT1Time(it) }
                )
            }
        }

        // Bike
        item {
            PaceRechnerCard(
                backgroundColor = de.pierrelaub.pace_rechner.ui.theme.BikeColor,
                textColor = de.pierrelaub.pace_rechner.ui.theme.BikeOnColor,
                title = "Bike"
            ) {
                PaceRechnerForm(
                    title = "Bike",
                    backgroundColor = de.pierrelaub.pace_rechner.ui.theme.BikeColor,
                    textColor = de.pierrelaub.pace_rechner.ui.theme.BikeOnColor,
                    distance = viewModel.bikeDistance,
                    onDistanceChange = { viewModel.updateBikeDistance(it) },
                    time = viewModel.bikeTime,
                    onTimeChange = { viewModel.updateBikeTime(it) },
                    speed = viewModel.bikeSpeed,
                    onSpeedChange = { viewModel.updateBikeSpeed(it) },
                    paceType = PaceType.Speed,
                    distanceUnit = DistanceUnit.Bike
                )
            }
        }

        // T2
        item {
            PaceRechnerCard(
                backgroundColor = de.pierrelaub.pace_rechner.ui.theme.TransitionColor,
                textColor = de.pierrelaub.pace_rechner.ui.theme.TransitionOnColor,
                title = "T2"
            ) {
                PaceRechnerTransition(
                    time = viewModel.t2Time,
                    onTimeChange = { viewModel.updateT2Time(it) }
                )
            }
        }

        // Run
        item {
            PaceRechnerCard(
                backgroundColor = de.pierrelaub.pace_rechner.ui.theme.RunColor,
                textColor = de.pierrelaub.pace_rechner.ui.theme.RunOnColor,
                title = "Run"
            ) {
                PaceRechnerForm(
                    title = "Run",
                    backgroundColor = de.pierrelaub.pace_rechner.ui.theme.RunColor,
                    textColor = de.pierrelaub.pace_rechner.ui.theme.RunOnColor,
                    distance = viewModel.runDistance,
                    onDistanceChange = { viewModel.updateRunDistance(it) },
                    time = viewModel.runTime,
                    onTimeChange = { viewModel.updateRunTime(it) },
                    pace = viewModel.runPace,
                    onPaceChange = { viewModel.updateRunPace(it) },
                    paceType = PaceType.Pace,
                    paceUnit = PaceUnit.Run,
                    distanceUnit = DistanceUnit.Run
                )
            }
        }

        // Summary
        item {
            PaceRechnerSummary(
                swimTimeString = secondsToHHMMSS(viewModel.swimTime, false),
                bikeTimeString = secondsToHHMMSS(viewModel.bikeTime, false),
                runTimeString = secondsToHHMMSS(viewModel.runTime, false),
                t1TimeString = secondsToHHMMSS(viewModel.t1Time, false),
                t2TimeString = secondsToHHMMSS(viewModel.t2Time, false),
                totalTimeString = secondsToHHMMSS(viewModel.totalTime, false),
                swimCumulativeTimeString = secondsToHHMMSS(viewModel.swimCumulativeTime, false),
                t1CumulativeTimeString = secondsToHHMMSS(viewModel.t1CumulativeTime, false),
                bikeCumulativeTimeString = secondsToHHMMSS(viewModel.bikeCumulativeTime, false),
                t2CumulativeTimeString = secondsToHHMMSS(viewModel.t2CumulativeTime, false),
                dayTimeStartString = secondsToHHMMSS(viewModel.dayTimeStart, false),
                totalTimeAfterSwimString = secondsToHHMMSS(viewModel.totalTimeAfterSwim, false),
                timeAfterT1String = secondsToHHMMSS(viewModel.timeAfterT1, false),
                totalTimeAfterBikeString = secondsToHHMMSS(viewModel.totalTimeAfterBike, false),
                timeAfterT2String = secondsToHHMMSS(viewModel.timeAfterT2, false),
                dayTimeFinish = secondsToHHMMSS(viewModel.dayTimeFinish, false),
                bikeQuarter1Km = viewModel.bikeQuarter1Km,
                bikeHalfKm = viewModel.bikeHalfKm,
                bikeThreeQuarterKm = viewModel.bikeThreeQuarterKm,
                runQuarter1Km = viewModel.runQuarter1Km,
                runHalfKm = viewModel.runHalfKm,
                runThreeQuarterKm = viewModel.runThreeQuarterKm,
                bike25TimeString = secondsToHHMMSS(viewModel.bike25Time, false),
                bike50TimeString = secondsToHHMMSS(viewModel.bike50Time, false),
                bike75TimeString = secondsToHHMMSS(viewModel.bike75Time, false),
                run25TimeString = secondsToHHMMSS(viewModel.run25Time, false),
                run50TimeString = secondsToHHMMSS(viewModel.run50Time, false),
                run75TimeString = secondsToHHMMSS(viewModel.run75Time, false),
                bike25CumulativeTimeString = secondsToHHMMSS(viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike25Time, false),
                bike50CumulativeTimeString = secondsToHHMMSS(viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike50Time, false),
                bike75CumulativeTimeString = secondsToHHMMSS(viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike75Time, false),
                run25CumulativeTimeString = secondsToHHMMSS(viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run25Time, false),
                run50CumulativeTimeString = secondsToHHMMSS(viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run50Time, false),
                run75CumulativeTimeString = secondsToHHMMSS(viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run75Time, false),
                clockTimeBike25String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike25Time, false),
                clockTimeBike50String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike50Time, false),
                clockTimeBike75String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.swimCumulativeTime + viewModel.t1CumulativeTime + viewModel.bike75Time, false),
                clockTimeRun25String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run25Time, false),
                clockTimeRun50String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run50Time, false),
                clockTimeRun75String = secondsToHHMMSS(viewModel.dayTimeStart + viewModel.bikeCumulativeTime + viewModel.t2CumulativeTime + viewModel.run75Time, false)
            )
        }
    }
}

@Composable
fun PaceRechnerCard(
    backgroundColor: Color,
    textColor: Color,
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(backgroundColor)
                .padding(top = 6.dp)
        ) {
            // Title badge
            Card(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            content()
        }
    }
}

@Preview
@Composable
private fun PaceRechnerPreview() {
    PaceRechner()
}
