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
import de.pierrelaub.pace_rechner.util.secondsToHHMMSS
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaceRechner(
    modifier: Modifier = Modifier
) {
    // State variables for all the data - initialized based on settings
    var dayTimeStart by remember { mutableStateOf(25200) } // 07:00:00 in seconds
    var swimDistance by remember { mutableStateOf(3800.0) }
    var swimTime by remember { mutableStateOf(4560) }
    var swimPace by remember { mutableStateOf(120) }
    var bikeDistance by remember { mutableStateOf(180.0) }
    var bikeTime by remember { mutableStateOf(25920) }
    var bikeSpeed by remember { mutableStateOf(25.0) }
    var runDistance by remember { mutableStateOf(42195.0) }
    var runTime by remember { mutableStateOf(16034) }
    var runPace by remember { mutableStateOf(380) }
    var t1Time by remember { mutableStateOf(120) } // 2 minutes
    var t2Time by remember { mutableStateOf(90) } // 1.5 minutes

    var preset by remember { mutableStateOf("") }
    var presetExpanded by remember { mutableStateOf(false) }

    // Define handlePresetChange function before using it
    fun handlePresetChange(selectedPreset: String) {
        when (selectedPreset) {
            "sprint" -> {
                swimDistance = 750.0
                swimPace = 120
                swimTime = 900

                bikeDistance = 20.0
                bikeSpeed = 25.0
                bikeTime = 2880

                runDistance = 5000.0
                runPace = 300
                runTime = 1500
            }
            "olympic" -> {
                swimDistance = 1500.0
                swimPace = 120
                swimTime = 1800

                bikeDistance = 40.0
                bikeSpeed = 25.0
                bikeTime = 5760

                runDistance = 10000.0
                runPace = 300
                runTime = 3000
            }
            "md" -> {
                swimDistance = 1900.0
                swimPace = 120
                swimTime = 2280

                bikeDistance = 90.0
                bikeSpeed = 25.0
                bikeTime = 12960

                runDistance = 21097.5
                runPace = 380
                runTime = 8017
            }
            "ld" -> {
                swimDistance = 3800.0
                swimPace = 120
                swimTime = 4560

                bikeDistance = 180.0
                bikeSpeed = 25.0
                bikeTime = 25920

                runDistance = 42195.0
                runPace = 380
                runTime = 16034
            }
        }
    }

    // Load default distance setting on first composition
    LaunchedEffect(Unit) {
        val defaultDistance = SettingsRepository.getDefaultDistance()
        handlePresetChange(defaultDistance)
    }

    val presetOptions = listOf(
        "" to "Vorlagen",
        "sprint" to "Sprint",
        "olympic" to "Olympisch",
        "md" to "Mitteldistanz",
        "ld" to "Langdistanz"
    )

    // Calculated values
    val totalTime = swimTime + t1Time + bikeTime + t2Time + runTime
    val swimCumulativeTime = swimTime
    val t1CumulativeTime = swimTime + t1Time
    val bikeCumulativeTime = swimTime + t1Time + bikeTime
    val t2CumulativeTime = swimTime + t1Time + bikeTime + t2Time

    // Time calculations for splits
    val bikeQuarter1Km = bikeDistance * 0.25
    val bikeHalfKm = bikeDistance * 0.5
    val bikeThreeQuarterKm = bikeDistance * 0.75
    val runQuarter1Km = runDistance * 0.25 / 1000.0
    val runHalfKm = runDistance * 0.5 / 1000.0
    val runThreeQuarterKm = runDistance * 0.75 / 1000.0

    val bike25Time = (bikeTime * 0.25).toInt()
    val bike50Time = (bikeTime * 0.5).toInt()
    val bike75Time = (bikeTime * 0.75).toInt()
    val run25Time = (runTime * 0.25).toInt()
    val run50Time = (runTime * 0.5).toInt()
    val run75Time = (runTime * 0.75).toInt()

    // Clock times
    val totalTimeAfterSwim = dayTimeStart + swimCumulativeTime
    val timeAfterT1 = dayTimeStart + t1CumulativeTime
    val totalTimeAfterBike = dayTimeStart + bikeCumulativeTime
    val timeAfterT2 = dayTimeStart + t2CumulativeTime
    val dayTimeFinish = dayTimeStart + totalTime

    // Use LazyColumn to fix the scrolling issue
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toolbar section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        ExposedDropdownMenuBox(
                            expanded = presetExpanded,
                            onExpandedChange = { presetExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = presetOptions.find { it.first == preset }?.second ?: "Vorlagen",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = presetExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .width(150.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF74B9FF),
                                    unfocusedBorderColor = Color(0xFFDDD6FE)
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = presetExpanded,
                                onDismissRequest = { presetExpanded = false }
                            ) {
                                presetOptions.forEach { (value, label) ->
                                    DropdownMenuItem(
                                        text = { Text(label) },
                                        onClick = {
                                            preset = value
                                            presetExpanded = false
                                            if (value.isNotEmpty()) {
                                                handlePresetChange(value)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Um wieviel Uhr startest du?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2D3436)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        DurationPicker(
                            totalSeconds = dayTimeStart,
                            onTotalSecondsChange = { dayTimeStart = it }
                        )
                    }


                }
            }
        }

        // Swim
        item {
            PaceRechnerCard(
                backgroundColor = Color(0xFF74B9FF),
                textColor = Color.White,
                title = "Swim"
            ) {
                PaceRechnerForm(
                    title = "Swim",
                    backgroundColor = Color(0xFF74B9FF),
                    textColor = Color.White,
                    distance = swimDistance,
                    onDistanceChange = { swimDistance = it },
                    time = swimTime,
                    onTimeChange = { swimTime = it },
                    pace = swimPace,
                    onPaceChange = { swimPace = it },
                    paceType = PaceType.Pace,
                    paceUnit = PaceUnit.Swim,
                    distanceUnit = DistanceUnit.Swim
                )
            }
        }

        // T1
        item {
            PaceRechnerCard(
                backgroundColor = Color(0xFFDDA0DD),
                textColor = Color.Black,
                title = "T1"
            ) {
                PaceRechnerTransition(
                    time = t1Time,
                    onTimeChange = { t1Time = it }
                )
            }
        }

        // Bike
        item {
            PaceRechnerCard(
                backgroundColor = Color(0xFF00B894),
                textColor = Color.White,
                title = "Bike"
            ) {
                PaceRechnerForm(
                    title = "Bike",
                    backgroundColor = Color(0xFF00B894),
                    textColor = Color.White,
                    distance = bikeDistance,
                    onDistanceChange = { bikeDistance = it },
                    time = bikeTime,
                    onTimeChange = { bikeTime = it },
                    speed = bikeSpeed,
                    onSpeedChange = { bikeSpeed = it },
                    paceType = PaceType.Speed,
                    distanceUnit = DistanceUnit.Bike
                )
            }
        }

        // T2
        item {
            PaceRechnerCard(
                backgroundColor = Color(0xFFDDA0DD),
                textColor = Color.Black,
                title = "T2"
            ) {
                PaceRechnerTransition(
                    time = t2Time,
                    onTimeChange = { t2Time = it }
                )
            }
        }

        // Run
        item {
            PaceRechnerCard(
                backgroundColor = Color(0xFFFAB1A0),
                textColor = Color(0xFF2D3436),
                title = "Run"
            ) {
                PaceRechnerForm(
                    title = "Run",
                    backgroundColor = Color(0xFFFAB1A0),
                    textColor = Color(0xFF2D3436),
                    distance = runDistance,
                    onDistanceChange = { runDistance = it },
                    time = runTime,
                    onTimeChange = { runTime = it },
                    pace = runPace,
                    onPaceChange = { runPace = it },
                    paceType = PaceType.Pace,
                    paceUnit = PaceUnit.Run,
                    distanceUnit = DistanceUnit.Run
                )
            }
        }

        // Summary
        item {
            PaceRechnerSummary(
                swimTimeString = secondsToHHMMSS(swimTime, false),
                bikeTimeString = secondsToHHMMSS(bikeTime, false),
                runTimeString = secondsToHHMMSS(runTime, false),
                t1TimeString = secondsToHHMMSS(t1Time, false),
                t2TimeString = secondsToHHMMSS(t2Time, false),
                totalTimeString = secondsToHHMMSS(totalTime, false),
                swimCumulativeTimeString = secondsToHHMMSS(swimCumulativeTime, false),
                t1CumulativeTimeString = secondsToHHMMSS(t1CumulativeTime, false),
                bikeCumulativeTimeString = secondsToHHMMSS(bikeCumulativeTime, false),
                t2CumulativeTimeString = secondsToHHMMSS(t2CumulativeTime, false),
                dayTimeStartString = secondsToHHMMSS(dayTimeStart, false),
                totalTimeAfterSwimString = secondsToHHMMSS(totalTimeAfterSwim, false),
                timeAfterT1String = secondsToHHMMSS(timeAfterT1, false),
                totalTimeAfterBikeString = secondsToHHMMSS(totalTimeAfterBike, false),
                timeAfterT2String = secondsToHHMMSS(timeAfterT2, false),
                dayTimeFinish = secondsToHHMMSS(dayTimeFinish, false),
                bikeQuarter1Km = bikeQuarter1Km,
                bikeHalfKm = bikeHalfKm,
                bikeThreeQuarterKm = bikeThreeQuarterKm,
                runQuarter1Km = runQuarter1Km,
                runHalfKm = runHalfKm,
                runThreeQuarterKm = runThreeQuarterKm,
                bike25TimeString = secondsToHHMMSS(bike25Time, false),
                bike50TimeString = secondsToHHMMSS(bike50Time, false),
                bike75TimeString = secondsToHHMMSS(bike75Time, false),
                run25TimeString = secondsToHHMMSS(run25Time, false),
                run50TimeString = secondsToHHMMSS(run50Time, false),
                run75TimeString = secondsToHHMMSS(run75Time, false),
                bike25CumulativeTimeString = secondsToHHMMSS(swimCumulativeTime + t1CumulativeTime + bike25Time, false),
                bike50CumulativeTimeString = secondsToHHMMSS(swimCumulativeTime + t1CumulativeTime + bike50Time, false),
                bike75CumulativeTimeString = secondsToHHMMSS(swimCumulativeTime + t1CumulativeTime + bike75Time, false),
                run25CumulativeTimeString = secondsToHHMMSS(bikeCumulativeTime + t2CumulativeTime + run25Time, false),
                run50CumulativeTimeString = secondsToHHMMSS(bikeCumulativeTime + t2CumulativeTime + run50Time, false),
                run75CumulativeTimeString = secondsToHHMMSS(bikeCumulativeTime + t2CumulativeTime + run75Time, false),
                clockTimeBike25String = secondsToHHMMSS(dayTimeStart + swimCumulativeTime + t1CumulativeTime + bike25Time, false),
                clockTimeBike50String = secondsToHHMMSS(dayTimeStart + swimCumulativeTime + t1CumulativeTime + bike50Time, false),
                clockTimeBike75String = secondsToHHMMSS(dayTimeStart + swimCumulativeTime + t1CumulativeTime + bike75Time, false),
                clockTimeRun25String = secondsToHHMMSS(dayTimeStart + bikeCumulativeTime + t2CumulativeTime + run25Time, false),
                clockTimeRun50String = secondsToHHMMSS(dayTimeStart + bikeCumulativeTime + t2CumulativeTime + run50Time, false),
                clockTimeRun75String = secondsToHHMMSS(dayTimeStart + bikeCumulativeTime + t2CumulativeTime + run75Time, false)
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D3436)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
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
