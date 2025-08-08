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

@Composable
fun PaceRechnerSummary(
    swimTimeString: String = "00:00:00",
    bikeTimeString: String = "00:00:00",
    runTimeString: String = "00:00:00",
    t1TimeString: String = "00:00:00",
    t2TimeString: String = "00:00:00",
    totalTimeString: String = "00:00:00",
    swimCumulativeTimeString: String = "00:00:00",
    t1CumulativeTimeString: String = "00:00:00",
    bikeCumulativeTimeString: String = "00:00:00",
    t2CumulativeTimeString: String = "00:00:00",
    dayTimeStartString: String = "00:00:00",
    totalTimeAfterSwimString: String = "00:00:00",
    timeAfterT1String: String = "00:00:00",
    totalTimeAfterBikeString: String = "00:00:00",
    timeAfterT2String: String = "00:00:00",
    dayTimeFinish: String = "00:00:00",
    bikeQuarter1Km: Double = 0.0,
    bikeHalfKm: Double = 0.0,
    bikeThreeQuarterKm: Double = 0.0,
    runQuarter1Km: Double = 0.0,
    runHalfKm: Double = 0.0,
    runThreeQuarterKm: Double = 0.0,
    bike25TimeString: String = "00:00:00",
    bike50TimeString: String = "00:00:00",
    bike75TimeString: String = "00:00:00",
    run25TimeString: String = "00:00:00",
    run50TimeString: String = "00:00:00",
    run75TimeString: String = "00:00:00",
    bike25CumulativeTimeString: String = "00:00:00",
    bike50CumulativeTimeString: String = "00:00:00",
    bike75CumulativeTimeString: String = "00:00:00",
    run25CumulativeTimeString: String = "00:00:00",
    run50CumulativeTimeString: String = "00:00:00",
    run75CumulativeTimeString: String = "00:00:00",
    clockTimeBike25String: String = "00:00:00",
    clockTimeBike50String: String = "00:00:00",
    clockTimeBike75String: String = "00:00:00",
    clockTimeRun25String: String = "00:00:00",
    clockTimeRun50String: String = "00:00:00",
    clockTimeRun75String: String = "00:00:00",
    modifier: Modifier = Modifier
) {
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
                text = "Triathlon Zusammenfassung",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SummarySection("Zeiten") {
                SummaryRow("Schwimmen:", swimTimeString)
                SummaryRow("T1:", t1TimeString)
                SummaryRow("Radfahren:", bikeTimeString)
                SummaryRow("T2:", t2TimeString)
                SummaryRow("Laufen:", runTimeString)
                SummaryRow("Gesamt:", totalTimeString, isTotal = true)
            }

            SummarySection("Kumulierte Zeiten") {
                SummaryRow("Nach Schwimmen:", swimCumulativeTimeString)
                SummaryRow("Nach T1:", t1CumulativeTimeString)
                SummaryRow("Nach Radfahren:", bikeCumulativeTimeString)
                SummaryRow("Nach T2:", t2CumulativeTimeString)
                SummaryRow("Nach Laufen:", totalTimeString, isTotal = true)
            }

            SummarySection("Tageszeiten") {
                SummaryRow("Start:", dayTimeStartString)
                SummaryRow("Nach Schwimmen:", totalTimeAfterSwimString)
                SummaryRow("Nach T1:", timeAfterT1String)
                SummaryRow("Nach Radfahren:", totalTimeAfterBikeString)
                SummaryRow("Nach T2:", timeAfterT2String)
                SummaryRow("Ziel:", dayTimeFinish, isTotal = true)
            }

            SummarySection("Zwischenzeiten Radfahren") {
                SummaryRow("25% (${bikeQuarter1Km}km):", bike25TimeString)
                SummaryRow("50% (${bikeHalfKm}km):", bike50TimeString)
                SummaryRow("75% (${bikeThreeQuarterKm}km):", bike75TimeString)
            }

            SummarySection("Zwischenzeiten Laufen") {
                SummaryRow("25% (${runQuarter1Km}km):", run25TimeString)
                SummaryRow("50% (${runHalfKm}km):", run50TimeString)
                SummaryRow("75% (${runThreeQuarterKm}km):", run75TimeString)
            }
        }
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
        PaceRechnerSummary(
            swimTimeString = "00:30:00",
            bikeTimeString = "02:30:00",
            runTimeString = "01:45:00",
            t1TimeString = "00:02:00",
            t2TimeString = "00:01:30",
            totalTimeString = "05:08:30",
            dayTimeStartString = "07:00:00",
            dayTimeFinish = "12:08:30"
        )
    }
}
