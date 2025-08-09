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
import de.pierrelaub.pace_rechner.resources.LocalizedStrings

@Composable
fun PaceRechnerSummary(
    summaryViewModel: PaceRechnerSummaryViewModel,
    modifier: Modifier = Modifier
) {
    val strings = LocalizedStrings()
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
                text = "${competitionType.displayName} ${strings.summary}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Dynamic sections based on competition type
            DynamicSummaryContent(competitionType, summaryViewModel, strings)
        }
    }
}

@Composable
private fun DynamicSummaryContent(
    competitionType: CompetitionType,
    summaryViewModel: PaceRechnerSummaryViewModel,
    strings: de.pierrelaub.pace_rechner.resources.Strings
) {
    // Get activities from the current competition type configuration
    val activities = remember(competitionType) {
        competitionType.sportsTypes
    }

    // Section: Individual Times
    SummarySection("${strings.time}:") {
        activities.forEachIndexed { index, sportsType ->
            val activityName = getSportDisplayName(sportsType, index, competitionType, strings)
            SummaryRow("$activityName:", summaryViewModel.getActivityTimeString(index))

            // Add transition time after each sport (except the last one)
            if (index < activities.size - 1 && competitionType.hasTransitions) {
                val transitionName = if (index == 0) strings.transition1 else strings.transition2
                SummaryRow("$transitionName:", summaryViewModel.getTransitionTimeString(index))
            }
        }
        SummaryRow("${strings.totalTime}:", summaryViewModel.totalTimeString, isTotal = true)
    }

    // Section: Cumulative Times (only for multi-sport events)
    if (activities.size > 1) {
        SummarySection("Kumulierte Zeiten") {
            activities.forEachIndexed { index, sportsType ->
                val activityName = getSportDisplayName(sportsType, index, competitionType, strings)
                SummaryRow("Nach $activityName:", summaryViewModel.getCumulativeTimeString(index))

                // Add cumulative time after transitions
                if (index < activities.size - 1 && competitionType.hasTransitions) {
                    val transitionName = if (index == 0) strings.transition1 else strings.transition2
                    SummaryRow("Nach $transitionName:", summaryViewModel.getCumulativeTimeWithTransitionString(index))
                }
            }
        }
    }

    // Section: Clock Times
    SummarySection("Tageszeiten") {
        SummaryRow("${strings.startTime}:", summaryViewModel.dayTimeStartString)

        activities.forEachIndexed { index, sportsType ->
            val activityName = getSportDisplayName(sportsType, index, competitionType, strings)
            SummaryRow("Nach $activityName:", summaryViewModel.getClockTimeString(index))

            // Add clock time after transitions
            if (index < activities.size - 1 && competitionType.hasTransitions) {
                // Clock time after transition needs to be calculated differently
                // This would need additional logic in the summary view model
            }
        }

        SummaryRow("${strings.finishTime}:", summaryViewModel.dayTimeFinish, isTotal = true)
    }
}

private fun getSportDisplayName(
    sportsType: SportsType,
    index: Int,
    competitionType: CompetitionType,
    strings: de.pierrelaub.pace_rechner.resources.Strings
): String {
    return when (competitionType) {
        CompetitionType.Duathlon -> {
            when {
                sportsType == SportsType.Run && index == 0 -> strings.run1
                sportsType == SportsType.Run && index == 2 -> strings.run2
                else -> when (sportsType) {
                    SportsType.Swim -> strings.swim
                    SportsType.Bike -> strings.bike
                    SportsType.Run -> strings.run
                    SportsType.Rowing -> strings.rowing
                    SportsType.Hiking -> strings.hiking
                    SportsType.Walking -> strings.walking
                }
            }
        }
        else -> when (sportsType) {
            SportsType.Swim -> strings.swim
            SportsType.Bike -> strings.bike
            SportsType.Run -> strings.run
            SportsType.Rowing -> strings.rowing
            SportsType.Hiking -> strings.hiking
            SportsType.Walking -> strings.walking
        }
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
