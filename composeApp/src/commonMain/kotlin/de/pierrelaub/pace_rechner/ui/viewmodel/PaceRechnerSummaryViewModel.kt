package de.pierrelaub.pace_rechner.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import de.pierrelaub.pace_rechner.utils.TimeFormatter

class PaceRechnerSummaryViewModel(private val paceRechnerViewModel: PaceRechnerViewModel) {

    // Grundzeiten
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

    val totalTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(totalTime)

    // Kumulierte Zeiten
    val swimCumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(swimCumulativeTime)

    val t1CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(t1CumulativeTime)

    val bikeCumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bikeCumulativeTime)

    val t2CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(t2CumulativeTime)

    // Tageszeiten
    val dayTimeStartString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart)

    val totalTimeAfterSwimString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + swimCumulativeTime)

    val timeAfterT1String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + t1CumulativeTime)

    val totalTimeAfterBikeString: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + bikeCumulativeTime)

    val timeAfterT2String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + t2CumulativeTime)

    val dayTimeFinish: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + totalTime)

    // Zwischendistanzen für Rad
    val bikeQuarter1Km: Double
        @Composable get() = paceRechnerViewModel.bikeDistance * 0.25

    val bikeHalfKm: Double
        @Composable get() = paceRechnerViewModel.bikeDistance * 0.5

    val bikeThreeQuarterKm: Double
        @Composable get() = paceRechnerViewModel.bikeDistance * 0.75

    // Zwischendistanzen für Lauf
    val runQuarter1Km: Double
        @Composable get() = paceRechnerViewModel.runDistance / 1000.0 * 0.25

    val runHalfKm: Double
        @Composable get() = paceRechnerViewModel.runDistance / 1000.0 * 0.5

    val runThreeQuarterKm: Double
        @Composable get() = paceRechnerViewModel.runDistance / 1000.0 * 0.75

    // Zwischenzeiten Rad
    val bike25TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bike25Time)

    val bike50TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bike50Time)

    val bike75TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bike75Time)

    // Zwischenzeiten Lauf
    val run25TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(run25Time)

    val run50TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(run50Time)

    val run75TimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(run75Time)

    // Kumulierte Zwischenzeiten Rad
    val bike25CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(t1CumulativeTime + bike25Time)

    val bike50CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(t1CumulativeTime + bike50Time)

    val bike75CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(t1CumulativeTime + bike75Time)

    // Kumulierte Zwischenzeiten Lauf
    val run25CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bikeCumulativeTime + paceRechnerViewModel.t2Time + run25Time)

    val run50CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bikeCumulativeTime + paceRechnerViewModel.t2Time + run50Time)

    val run75CumulativeTimeString: String
        @Composable get() = TimeFormatter.formatSecondsToTimeString(bikeCumulativeTime + paceRechnerViewModel.t2Time + run75Time)

    // Tageszeiten für Zwischenzeiten Rad
    val clockTimeBike25String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + t1CumulativeTime + bike25Time)

    val clockTimeBike50String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + t1CumulativeTime + bike50Time)

    val clockTimeBike75String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + t1CumulativeTime + bike75Time)

    // Tageszeiten für Zwischenzeiten Lauf
    val clockTimeRun25String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + bikeCumulativeTime + paceRechnerViewModel.t2Time + run25Time)

    val clockTimeRun50String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + bikeCumulativeTime + paceRechnerViewModel.t2Time + run50Time)

    val clockTimeRun75String: String
        @Composable get() = TimeFormatter.formatSecondsToDayTime(paceRechnerViewModel.dayTimeStart + bikeCumulativeTime + paceRechnerViewModel.t2Time + run75Time)

    // Private berechnete Werte
    private val totalTime: Int
        @Composable get() = paceRechnerViewModel.swimTime + paceRechnerViewModel.t1Time +
                           paceRechnerViewModel.bikeTime + paceRechnerViewModel.t2Time +
                           paceRechnerViewModel.runTime

    private val swimCumulativeTime: Int
        @Composable get() = paceRechnerViewModel.swimTime

    private val t1CumulativeTime: Int
        @Composable get() = paceRechnerViewModel.swimTime + paceRechnerViewModel.t1Time

    private val bikeCumulativeTime: Int
        @Composable get() = paceRechnerViewModel.swimTime + paceRechnerViewModel.t1Time + paceRechnerViewModel.bikeTime

    private val t2CumulativeTime: Int
        @Composable get() = paceRechnerViewModel.swimTime + paceRechnerViewModel.t1Time +
                           paceRechnerViewModel.bikeTime + paceRechnerViewModel.t2Time

    private val bike25Time: Int
        @Composable get() = (paceRechnerViewModel.bikeTime * 0.25).toInt()

    private val bike50Time: Int
        @Composable get() = (paceRechnerViewModel.bikeTime * 0.5).toInt()

    private val bike75Time: Int
        @Composable get() = (paceRechnerViewModel.bikeTime * 0.75).toInt()

    private val run25Time: Int
        @Composable get() = (paceRechnerViewModel.runTime * 0.25).toInt()

    private val run50Time: Int
        @Composable get() = (paceRechnerViewModel.runTime * 0.5).toInt()

    private val run75Time: Int
        @Composable get() = (paceRechnerViewModel.runTime * 0.75).toInt()
}
