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
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class TabItem(
    val title: String,
    val emoji: String
) {
    PACE_RECHNER("Rechner", "🧮"),
    HISTORY("History", "📊"),
    SETTINGS("Settings", "⚙️")
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(TabItem.PACE_RECHNER) }

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
                TabItem.PACE_RECHNER -> PaceRechnerScreen()
                TabItem.HISTORY -> HistoryScreen()
                TabItem.SETTINGS -> SettingsScreen()
            }
        }

        // Bottom Navigation
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ) {
                TabItem.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = {
                            Text(
                                text = tab.emoji,
                                fontSize = 20.sp
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
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
