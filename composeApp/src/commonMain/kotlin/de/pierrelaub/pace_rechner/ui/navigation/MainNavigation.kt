package de.pierrelaub.pace_rechner.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
        NavigationBar(
            containerColor = Color.White,
            contentColor = Color(0xFF2D3436)
        ) {
            TabItem.entries.forEach { tab ->
                NavigationBarItem(
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab },
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
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF74B9FF),
                        selectedTextColor = Color(0xFF74B9FF),
                        unselectedIconColor = Color(0xFF636E72),
                        unselectedTextColor = Color(0xFF636E72),
                        indicatorColor = Color(0xFF74B9FF).copy(alpha = 0.1f)
                    )
                )
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
