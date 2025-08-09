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
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerSummaryViewModel
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

@Composable
fun PaceRechnerSummary(
    summaryViewModel: PaceRechnerSummaryViewModel,
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
                SummaryRow("Schwimmen:", summaryViewModel.swimTimeString)
                SummaryRow("T1:", summaryViewModel.t1TimeString)
                SummaryRow("Radfahren:", summaryViewModel.bikeTimeString)
                SummaryRow("T2:", summaryViewModel.t2TimeString)
                SummaryRow("Laufen:", summaryViewModel.runTimeString)
                SummaryRow("Gesamt:", summaryViewModel.totalTimeString, isTotal = true)
            }

            SummarySection("Kumulierte Zeiten") {
                SummaryRow("Nach Schwimmen:", summaryViewModel.swimCumulativeTimeString)
                SummaryRow("Nach T1:", summaryViewModel.t1CumulativeTimeString)
                SummaryRow("Nach Radfahren:", summaryViewModel.bikeCumulativeTimeString)
                SummaryRow("Nach T2:", summaryViewModel.t2CumulativeTimeString)
                SummaryRow("Nach Laufen:", summaryViewModel.totalTimeString, isTotal = true)
            }

            SummarySection("Tageszeiten") {
                SummaryRow("Start:", summaryViewModel.dayTimeStartString)
                SummaryRow("Nach Schwimmen:", summaryViewModel.totalTimeAfterSwimString)
                SummaryRow("Nach T1:", summaryViewModel.timeAfterT1String)
                SummaryRow("Nach Radfahren:", summaryViewModel.totalTimeAfterBikeString)
                SummaryRow("Nach T2:", summaryViewModel.timeAfterT2String)
                SummaryRow("Ziel:", summaryViewModel.dayTimeFinish, isTotal = true)
            }

            SummarySection("Zwischenzeiten Radfahren") {
                SummaryRow("25% (${summaryViewModel.bikeQuarter1Km}km):", summaryViewModel.bike25TimeString)
                SummaryRow("50% (${summaryViewModel.bikeHalfKm}km):", summaryViewModel.bike50TimeString)
                SummaryRow("75% (${summaryViewModel.bikeThreeQuarterKm}km):", summaryViewModel.bike75TimeString)
            }

            SummarySection("Zwischenzeiten Laufen") {
                SummaryRow("25% (${summaryViewModel.runQuarter1Km}km):", summaryViewModel.run25TimeString)
                SummaryRow("50% (${summaryViewModel.runHalfKm}km):", summaryViewModel.run50TimeString)
                SummaryRow("75% (${summaryViewModel.runThreeQuarterKm}km):", summaryViewModel.run75TimeString)
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
        val paceViewModel = PaceRechnerViewModel()
        val summaryViewModel = PaceRechnerSummaryViewModel(paceViewModel)
        PaceRechnerSummary(summaryViewModel = summaryViewModel)
    }
}
