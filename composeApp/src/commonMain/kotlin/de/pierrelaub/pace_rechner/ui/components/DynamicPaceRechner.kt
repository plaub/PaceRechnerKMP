package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import de.pierrelaub.pace_rechner.data.SettingsRepository
import de.pierrelaub.pace_rechner.types.DistanceUnit
import de.pierrelaub.pace_rechner.types.PaceType
import de.pierrelaub.pace_rechner.types.PaceUnit
import de.pierrelaub.pace_rechner.types.SportsType
import de.pierrelaub.pace_rechner.types.CompetitionType
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerSummaryViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicPaceRechner(
    modifier: Modifier = Modifier,
    viewModel: PaceRechnerViewModel = remember { PaceRechnerViewModel() }
) {
    // Get current competition type from settings
    val competitionType by SettingsRepository.competitionType

    // Create summary view model
    val summaryViewModel = remember(viewModel) {
        PaceRechnerSummaryViewModel(viewModel)
    }

    // Get available presets for current competition type
    val presetOptions = remember(competitionType) {
        val baseOptions = listOf("" to "Vorlagen")
        val availablePresets = competitionType.getAvailablePresets()
        if (availablePresets.isNotEmpty()) {
            baseOptions + availablePresets
        } else {
            baseOptions
        }
    }

    // Use LazyColumn to fix the scrolling issue
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Toolbar section (only show if presets are available)
        if (competitionType.getAvailablePresets().isNotEmpty()) {
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
                                    .width(200.dp),
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
                                        placeholder = { Text("z.B. ${competitionType.displayName} Training") },
                                        singleLine = true
                                    )
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            if (saveName.isNotBlank()) {
                                                // Save with current competition type activities
                                                val activities = viewModel.activities
                                                de.pierrelaub.pace_rechner.data.HistoryRepository.saveCalculation(
                                                    name = saveName,
                                                    swimDistance = activities.getOrNull(0)?.distance ?: 0.0,
                                                    swimTime = activities.getOrNull(0)?.time ?: 0,
                                                    swimPace = activities.getOrNull(0)?.paceOrSpeed?.toInt() ?: 0,
                                                    bikeDistance = activities.getOrNull(1)?.distance ?: 0.0,
                                                    bikeTime = activities.getOrNull(1)?.time ?: 0,
                                                    bikeSpeed = activities.getOrNull(1)?.paceOrSpeed ?: 0.0,
                                                    runDistance = activities.getOrNull(2)?.distance ?: 0.0,
                                                    runTime = activities.getOrNull(2)?.time ?: 0,
                                                    runPace = activities.getOrNull(2)?.paceOrSpeed?.toInt() ?: 0,
                                                    t1Time = viewModel.transitionTimes[0] ?: 0,
                                                    t2Time = viewModel.transitionTimes[1] ?: 0,
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
        }

        // Dynamic sport activities based on competition type
        itemsIndexed(viewModel.activities) { index, activity ->
            val sportConfig = getSportConfiguration(activity.type)

            PaceRechnerCard(
                backgroundColor = sportConfig.backgroundColor,
                textColor = sportConfig.textColor,
                title = getSportTitle(activity.type, index, competitionType)
            ) {
                PaceRechnerForm(
                    title = getSportTitle(activity.type, index, competitionType),
                    backgroundColor = sportConfig.backgroundColor,
                    textColor = sportConfig.textColor,
                    distance = activity.distance,
                    onDistanceChange = { viewModel.updateActivityDistance(index, it) },
                    time = activity.time,
                    onTimeChange = { viewModel.updateActivityTime(index, it) },
                    pace = if (activity.type.isPace) activity.paceOrSpeed.toInt() else 0,
                    onPaceChange = if (activity.type.isPace) { pace -> viewModel.updateActivityPaceOrSpeed(index, pace.toDouble()) } else { _ -> },
                    speed = if (!activity.type.isPace) activity.paceOrSpeed else 0.0,
                    onSpeedChange = if (!activity.type.isPace) { speed -> viewModel.updateActivityPaceOrSpeed(index, speed) } else { _ -> },
                    paceType = if (activity.type.isPace) PaceType.Pace else PaceType.Speed,
                    paceUnit = when (activity.type) {
                        SportsType.Swim -> PaceUnit.Swim
                        SportsType.Run, SportsType.Hiking, SportsType.Walking -> PaceUnit.Run
                        else -> PaceUnit.Run // Default fallback
                    },
                    distanceUnit = when (activity.type) {
                        SportsType.Swim, SportsType.Run, SportsType.Hiking, SportsType.Walking -> DistanceUnit.Run
                        SportsType.Bike, SportsType.Rowing -> DistanceUnit.Bike
                    }
                )
            }

            // Add transition after each sport (except the last one)
            if (index < viewModel.activities.size - 1 && competitionType.hasTransitions) {
                PaceRechnerCard(
                    backgroundColor = de.pierrelaub.pace_rechner.ui.theme.TransitionColor,
                    textColor = de.pierrelaub.pace_rechner.ui.theme.TransitionOnColor,
                    title = "T${index + 1}"
                ) {
                    PaceRechnerTransition(
                        time = viewModel.transitionTimes[index] ?: 0,
                        onTimeChange = { viewModel.updateTransitionTime(index, it) }
                    )
                }
            }
        }

        // Summary
        item {
            PaceRechnerSummary(
                summaryViewModel = summaryViewModel
            )
        }
    }
}

data class SportConfiguration(
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
private fun getSportConfiguration(sportsType: SportsType): SportConfiguration {
    return when (sportsType) {
        SportsType.Swim -> SportConfiguration(
            de.pierrelaub.pace_rechner.ui.theme.SwimColor,
            de.pierrelaub.pace_rechner.ui.theme.SwimOnColor
        )
        SportsType.Bike -> SportConfiguration(
            de.pierrelaub.pace_rechner.ui.theme.BikeColor,
            de.pierrelaub.pace_rechner.ui.theme.BikeOnColor
        )
        SportsType.Run -> SportConfiguration(
            de.pierrelaub.pace_rechner.ui.theme.RunColor,
            de.pierrelaub.pace_rechner.ui.theme.RunOnColor
        )
        SportsType.Rowing -> SportConfiguration(
            Color(0xFF2E86AB), // Blau für Rudern
            Color.White
        )
        SportsType.Hiking -> SportConfiguration(
            Color(0xFF8B4513), // Braun für Wandern
            Color.White
        )
        SportsType.Walking -> SportConfiguration(
            Color(0xFF228B22), // Grün für Gehen
            Color.White
        )
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

private fun getSportTitle(sportsType: SportsType, index: Int, competitionType: CompetitionType): String {
    return when (competitionType) {
        CompetitionType.Duathlon -> {
            // For duathlon, differentiate between first and second run
            when {
                sportsType == SportsType.Run && index == 0 -> "Run 1"
                sportsType == SportsType.Run && index == 2 -> "Run 2"
                else -> sportsType.value
            }
        }
        else -> sportsType.value
    }
}

@Preview
@Composable
private fun DynamicPaceRechnerPreview() {
    DynamicPaceRechner()
}
