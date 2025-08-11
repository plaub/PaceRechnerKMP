package de.pierrelaub.pace_rechner.ui.viewmodel

import androidx.compose.runtime.Composable
import de.pierrelaub.pace_rechner.utils.TimeFormatter

class PaceRechnerSummaryViewModel(private val paceRechnerViewModel: PaceRechnerViewModel) {

    // Grundzeiten für alle Aktivitäten
    @Composable
    fun getActivityTimeString(index: Int): String {
        return if (index < paceRechnerViewModel.activities.size) {
            TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.activities[index].time)
        } else ""
    }

    @Composable
    fun getTransitionTimeString(index: Int): String {
        return TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.transitionTimes[index] ?: 0)
    }

    val totalTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.totalTime)

    // Kumulierte Zeiten für alle Aktivitäten
    @Composable
    fun getCumulativeTimeString(upToIndex: Int): String {
        // Calculate cumulative time including transitions up to the specified index
        var cumulativeTime = 0

        // Add activity times up to and including upToIndex
        for (i in 0..upToIndex) {
            if (i < paceRechnerViewModel.activities.size) {
                cumulativeTime += paceRechnerViewModel.activities[i].time
            }
        }

        // Add transition times up to (but not including) upToIndex
        for (i in 0 until upToIndex) {
            cumulativeTime += paceRechnerViewModel.transitionTimes[i] ?: 0
        }

        return TimeFormatter.formatSecondsToTimeString(cumulativeTime)
    }

    // New method: Cumulative time including transitions up to and including the specified index
    @Composable
    fun getCumulativeTimeWithTransitionString(upToIndex: Int): String {
        var cumulativeTime = 0

        // Add activity times up to and including upToIndex
        for (i in 0..upToIndex) {
            if (i < paceRechnerViewModel.activities.size) {
                cumulativeTime += paceRechnerViewModel.activities[i].time
            }
        }

        // Add transition times up to and including upToIndex
        for (i in 0..upToIndex) {
            cumulativeTime += paceRechnerViewModel.transitionTimes[i] ?: 0
        }

        return TimeFormatter.formatSecondsToTimeString(cumulativeTime)
    }

    // Tageszeiten
    val dayTimeStartString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart)

    @Composable
    fun getClockTimeString(upToIndex: Int): String {
        return TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.getClockTime(upToIndex))
    }

    val dayTimeFinish: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeFinish)
}
