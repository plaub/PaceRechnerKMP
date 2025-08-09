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
        return TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.getCumulativeTime(upToIndex))
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

    // Split-Distanzen für beliebige Aktivitäten
    @Composable
    fun getSplitDistance(activityIndex: Int, percentage: Double): Double {
        return if (activityIndex < paceRechnerViewModel.activities.size) {
            paceRechnerViewModel.activities[activityIndex].distance * percentage
        } else 0.0
    }

    // Split-Zeiten für beliebige Aktivitäten
    @Composable
    fun getSplitTimeString(activityIndex: Int, percentage: Double): String {
        val splitTime = if (activityIndex < paceRechnerViewModel.activities.size) {
            (paceRechnerViewModel.activities[activityIndex].time * percentage).toInt()
        } else 0
        return TimeFormatter.formatSecondsToTimeString(splitTime)
    }

    // Kumulierte Split-Zeiten
    @Composable
    fun getCumulativeSplitTimeString(activityIndex: Int, percentage: Double): String {
        val baseTime = paceRechnerViewModel.getCumulativeTime(activityIndex - 1) +
                      paceRechnerViewModel.transitionTimes.filterKeys { it < activityIndex }.values.sum()
        val splitTime = if (activityIndex < paceRechnerViewModel.activities.size) {
            (paceRechnerViewModel.activities[activityIndex].time * percentage).toInt()
        } else 0
        return TimeFormatter.formatSecondsToTimeString(baseTime + splitTime)
    }

    // Tageszeiten für Split-Zeiten
    @Composable
    fun getClockSplitTimeString(activityIndex: Int, percentage: Double): String {
        val baseTime = paceRechnerViewModel.getCumulativeTime(activityIndex - 1) +
                      paceRechnerViewModel.transitionTimes.filterKeys { it < activityIndex }.values.sum()
        val splitTime = if (activityIndex < paceRechnerViewModel.activities.size) {
            (paceRechnerViewModel.activities[activityIndex].time * percentage).toInt()
        } else 0
        val clockTime = paceRechnerViewModel.dayTimeStart + baseTime + splitTime
        return TimeFormatter.formatSecondsToDayTime(clockTime)
    }

    // Backward compatibility - bestehende Properties
    val swimTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.swimTime)

    val bikeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bikeTime)

    val runTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.runTime)

    val t1TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t1Time)

    val t2TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t2Time)

    // Kumulierte Zeiten
    val swimCumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.swimCumulativeTime)

    val t1CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t1CumulativeTime)

    val bikeCumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bikeCumulativeTime)

    val t2CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t2CumulativeTime)

    // Tageszeiten
    val totalTimeAfterSwimString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.totalTimeAfterSwim)

    val timeAfterT1String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.timeAfterT1)

    val totalTimeAfterBikeString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.totalTimeAfterBike)

    val timeAfterT2String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.timeAfterT2)

    // Zwischendistanzen für Rad
    val bikeQuarter1Km: Double
        @Composable get() = paceRechnerViewModel.bikeQuarter1Km

    val bikeHalfKm: Double
        @Composable get() = paceRechnerViewModel.bikeHalfKm

    val bikeThreeQuarterKm: Double
        @Composable get() = paceRechnerViewModel.bikeThreeQuarterKm

    // Zwischendistanzen für Lauf
    val runQuarter1Km: Double
        @Composable get() = paceRechnerViewModel.runQuarter1Km

    val runHalfKm: Double
        @Composable get() = paceRechnerViewModel.runHalfKm

    val runThreeQuarterKm: Double
        @Composable get() = paceRechnerViewModel.runThreeQuarterKm

    // Zwischenzeiten
    val bike25TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bike25Time)

    val bike50TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bike50Time)

    val bike75TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bike75Time)

    val run25TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.run25Time)

    val run50TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.run50Time)

    val run75TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.run75Time)

    // Kumulierte Zwischenzeiten Rad
    val bike25CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike25Time)

    val bike50CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike50Time)

    val bike75CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike75Time)

    // Kumulierte Zwischenzeiten Lauf
    val run25CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run25Time)

    val run50CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run50Time)

    val run75CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run75Time)

    // Tageszeiten für Zwischenzeiten Rad
    val clockTimeBike25String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike25Time)

    val clockTimeBike50String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike50Time)

    val clockTimeBike75String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.t1CumulativeTime + paceRechnerViewModel.bike75Time)

    // Tageszeiten für Zwischenzeiten Lauf
    val clockTimeRun25String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run25Time)

    val clockTimeRun50String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run50Time)

    val clockTimeRun75String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + paceRechnerViewModel.bikeCumulativeTime + paceRechnerViewModel.t2Time + paceRechnerViewModel.run75Time)
}
