package de.pierrelaub.pace_rechner.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.pierrelaub.pace_rechner.ui.screens.HistoryScreen
import de.pierrelaub.pace_rechner.ui.screens.PaceRechnerScreen
import de.pierrelaub.pace_rechner.ui.screens.SettingsScreen
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class TabItem(
    val titleKey: String,
    val emoji: String
) {
    PACE_RECHNER("calculator", "🧮"),
    HISTORY("history", "📊"),
    SETTINGS("settings", "⚙️")
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    val strings = LocalizedStrings()
    var selectedTab by remember { mutableStateOf(TabItem.PACE_RECHNER) }
    val sharedViewModel = remember { PaceRechnerViewModel() }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Content Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedTab) {
                TabItem.PACE_RECHNER -> PaceRechnerScreen(viewModel = sharedViewModel)
                TabItem.HISTORY -> HistoryScreen(
                    onLoadCalculation = { calculation ->
                        sharedViewModel.loadCalculation(calculation)
                        selectedTab = TabItem.PACE_RECHNER
                    }
                )
                TabItem.SETTINGS -> SettingsScreen()
            }
        }

        // Custom Bottom Navigation
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabItem.values().forEach { tab ->
                    val isSelected = selectedTab == tab
                    val title = when (tab.titleKey) {
                        "calculator" -> strings.calculator
                        "history" -> strings.history
                        "settings" -> strings.settings
                        else -> tab.titleKey
                    }

                    TabButton(
                        title = title,
                        emoji = tab.emoji,
                        isSelected = isSelected,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    title: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Transparent
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Preview
@Composable
private fun MainNavigationPreview() {
    MaterialTheme {
        MainNavigation()
    }
}
