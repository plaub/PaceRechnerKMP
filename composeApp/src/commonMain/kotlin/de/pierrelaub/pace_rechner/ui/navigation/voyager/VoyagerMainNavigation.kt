package de.pierrelaub.pace_rechner.ui.navigation.voyager

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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

@Composable
fun VoyagerMainNavigation(
    modifier: Modifier = Modifier
) {
    // Erstelle das ViewModel hier, damit es für alle Tabs geteilt wird
    val sharedViewModel = remember { PaceRechnerViewModel() }

    // Erstelle die Tab-Instanzen mit dem geteilten ViewModel
    val paceRechnerTab = remember { PaceRechnerTabWithViewModel(sharedViewModel) }
    val historyTab = remember { HistoryTabWithViewModel(sharedViewModel, paceRechnerTab) }
    val settingsTab = SettingsTab

    val tabs = listOf(paceRechnerTab, historyTab, settingsTab)

    TabNavigator(paceRechnerTab) { tabNavigator ->
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            // Content Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                tabNavigator.current.Content()
            }

            // Custom Bottom Navigation (keeping your existing design)
            VoyagerBottomNavigation(
                tabs = tabs,
                currentTab = tabNavigator.current,
                onTabSelected = { tab ->
                    tabNavigator.current = tab
                }
            )
        }
    }
}

@Composable
private fun VoyagerBottomNavigation(
    tabs: List<Tab>,
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    val strings = LocalizedStrings()

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
            tabs.forEach { tab ->
                val isSelected = currentTab == tab
                val (title, emoji) = when (tab) {
                    is PaceRechnerTabWithViewModel -> strings.calculator to "🧮"
                    is HistoryTabWithViewModel -> strings.history to "📊"
                    SettingsTab -> strings.settings to "⚙️"
                    else -> tab.options.title to "❓"
                }

                TabButton(
                    title = title,
                    emoji = emoji,
                    isSelected = isSelected,
                    onClick = { onTabSelected(tab) }
                )
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
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = if (isSelected) CardDefaults.cardElevation(defaultElevation = 4.dp) else CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                fontSize = 20.sp,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = contentColor
            )
        }
    }
}
