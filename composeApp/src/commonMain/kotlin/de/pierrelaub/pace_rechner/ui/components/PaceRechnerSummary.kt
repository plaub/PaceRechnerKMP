package de.pierrelaub.pace_rechner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import de.pierrelaub.pace_rechner.data.SettingsRepository
import de.pierrelaub.pace_rechner.types.CompetitionType
import de.pierrelaub.pace_rechner.types.SportsType
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerSummaryViewModel
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

@Composable
fun PaceRechnerSummary(
    summaryViewModel: PaceRechnerSummaryViewModel,
    modifier: Modifier = Modifier
) {
    // Get current competition type from settings
    val competitionType by SettingsRepository.competitionType

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${competitionType.displayName} Zusammenfassung",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Dynamic sections based on competition type
            DynamicSummaryContent(competitionType, summaryViewModel)
        }
    }
}

@Composable
private fun DynamicSummaryContent(
    competitionType: CompetitionType,
    summaryViewModel: PaceRechnerSummaryViewModel
) {
    // Get activities from the current competition type configuration
    val activities = remember(competitionType) {
        competitionType.sportsTypes
    }

    // Section: Individual Times
    SummarySection("Zeiten") {
        activities.forEachIndexed { index, sportsType ->
            val activityName = getSportDisplayName(sportsType, index, competitionType)
            SummaryRow("$activityName:", summaryViewModel.getActivityTimeString(index))

            // Add transition time after each sport (except the last one)
            if (index < activities.size - 1 && competitionType.hasTransitions) {
                SummaryRow("T${index + 1}:", summaryViewModel.getTransitionTimeString(index))
            }
        }
        SummaryRow("Gesamt:", summaryViewModel.totalTimeString, isTotal = true)
    }

    // Section: Cumulative Times (only for multi-sport events)
    if (activities.size > 1) {
        SummarySection("Kumulierte Zeiten") {
            activities.forEachIndexed { index, sportsType ->
                val activityName = getSportDisplayName(sportsType, index, competitionType)
                SummaryRow("Nach $activityName:", summaryViewModel.getCumulativeTimeString(index))

                // Add cumulative time after transitions
                if (index < activities.size - 1 && competitionType.hasTransitions) {
                    // Use the new method that includes the transition time
                    SummaryRow("Nach T${index + 1}:", summaryViewModel.getCumulativeTimeWithTransitionString(index))
                }
            }
        }
    }

    // Section: Clock Times
    SummarySection("Tageszeiten") {
        SummaryRow("Start:", summaryViewModel.dayTimeStartString)

        activities.forEachIndexed { index, sportsType ->
            val activityName = getSportDisplayName(sportsType, index, competitionType)
            SummaryRow("Nach $activityName:", summaryViewModel.getClockTimeString(index))

            // Add clock time after transitions
            if (index < activities.size - 1 && competitionType.hasTransitions) {
                // Clock time after transition needs to be calculated differently
                // This would need additional logic in the summary view model
            }
        }

        SummaryRow("Ziel:", summaryViewModel.dayTimeFinish, isTotal = true)
    }

    // Section: Split Times (only for activities that make sense)
    activities.forEachIndexed { index, sportsType ->
        if (shouldShowSplitTimes(sportsType)) {
            val activityName = getSportDisplayName(sportsType, index, competitionType)
            val distanceUnit = if (sportsType == SportsType.Bike || sportsType == SportsType.Rowing) "km" else "km"

            SummarySection("Zwischenzeiten $activityName") {
                val distance25 = summaryViewModel.getSplitDistance(index, 0.25)
                val distance50 = summaryViewModel.getSplitDistance(index, 0.5)
                val distance75 = summaryViewModel.getSplitDistance(index, 0.75)

                val divisor = if (sportsType == SportsType.Swim) 1000.0 else 1000.0

                // Simple formatting without .format()
                val formattedDistance25 = ((distance25 / divisor * 10).toInt() / 10.0).toString()
                val formattedDistance50 = ((distance50 / divisor * 10).toInt() / 10.0).toString()
                val formattedDistance75 = ((distance75 / divisor * 10).toInt() / 10.0).toString()

                SummaryRow(
                    "25% (${formattedDistance25}$distanceUnit):",
                    summaryViewModel.getSplitTimeString(index, 0.25)
                )
                SummaryRow(
                    "50% (${formattedDistance50}$distanceUnit):",
                    summaryViewModel.getSplitTimeString(index, 0.5)
                )
                SummaryRow(
                    "75% (${formattedDistance75}$distanceUnit):",
                    summaryViewModel.getSplitTimeString(index, 0.75)
                )
            }
        }
    }
}

@Composable
private fun getSportDisplayName(sportsType: SportsType, index: Int, competitionType: CompetitionType): String {
    return when (competitionType) {
        CompetitionType.Duathlon -> {
            // For duathlon, differentiate between first and second run
            when {
                sportsType == SportsType.Run && index == 0 -> "Laufen 1"
                sportsType == SportsType.Run && index == 2 -> "Laufen 2"
                else -> getSportTypeDisplayName(sportsType)
            }
        }
        else -> getSportTypeDisplayName(sportsType)
    }
}

@Composable
private fun getSportTypeDisplayName(sportsType: SportsType): String {
    return when (sportsType) {
        SportsType.Swim -> "Schwimmen"
        SportsType.Bike -> "Radfahren"
        SportsType.Run -> "Laufen"
        SportsType.Rowing -> "Rudern"
        SportsType.Hiking -> "Wandern"
        SportsType.Walking -> "Gehen"
    }
}

private fun shouldShowSplitTimes(sportsType: SportsType): Boolean {
    // Show split times for longer distance activities
    return when (sportsType) {
        SportsType.Bike, SportsType.Run, SportsType.Rowing -> true
        SportsType.Swim -> true // Could be made conditional based on distance
        SportsType.Hiking, SportsType.Walking -> true
    }
}

@Composable
private fun SummarySection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isTotal) 16.sp else 14.sp
        )
        Text(
            text = value,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal,
            fontSize = if (isTotal) 16.sp else 14.sp
        )
    }
}

@Preview
@Composable
private fun PaceRechnerSummaryPreview() {
    MaterialTheme {
        val paceViewModel = PaceRechnerViewModel()
        val summaryViewModel = PaceRechnerSummaryViewModel(paceViewModel)
        PaceRechnerSummary(summaryViewModel = summaryViewModel)
    }
}
