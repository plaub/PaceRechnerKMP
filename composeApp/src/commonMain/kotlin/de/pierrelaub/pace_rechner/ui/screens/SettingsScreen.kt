package de.pierrelaub.pace_rechner.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.pierrelaub.pace_rechner.data.SettingsRepository
import de.pierrelaub.pace_rechner.types.CompetitionType
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    // Use the repository for persistent settings
    val selectedDefaultDistance by SettingsRepository.defaultDistance
    val isDarkTheme by SettingsRepository.isDarkTheme
    val selectedCompetitionType by SettingsRepository.competitionType

    var defaultDistanceExpanded by remember { mutableStateOf(false) }
    var themeExpanded by remember { mutableStateOf(false) }
    var competitionTypeExpanded by remember { mutableStateOf(false) }

    // Competition type options
    val competitionTypeOptions = CompetitionType.entries.map { it to it.displayName }

    // Distance options based on selected competition type
    val distanceOptions = if (selectedCompetitionType.getAvailablePresets().isNotEmpty()) {
        selectedCompetitionType.getAvailablePresets()
    } else {
        listOf("" to "Keine Presets verfügbar")
    }

    val themeOptions = listOf(
        false to "Hell",
        true to "Dunkel"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Einstellungen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Konfiguriere deine App-Einstellungen.",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Einstellungen
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Competition Type Setting
                    DropdownSettingItem(
                        title = "Wettkampftyp",
                        description = "Wähle welche Sportarten angezeigt werden sollen",
                        selectedValue = selectedCompetitionType,
                        options = competitionTypeOptions,
                        expanded = competitionTypeExpanded,
                        onExpandedChange = { competitionTypeExpanded = it },
                        onValueChange = { newCompetitionType ->
                            SettingsRepository.setCompetitionType(newCompetitionType)
                            // Reset default distance when competition type changes
                            val newPresets = newCompetitionType.getAvailablePresets()
                            if (newPresets.isNotEmpty()) {
                                SettingsRepository.setDefaultDistance(newPresets.first().first)
                            } else {
                                SettingsRepository.setDefaultDistance("")
                            }
                        },
                        displayText = { it.displayName }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline
                    )

                    // Theme Setting
                    DropdownSettingItem(
                        title = "Theme",
                        description = "Wähle das Erscheinungsbild der App",
                        selectedValue = isDarkTheme,
                        options = themeOptions,
                        expanded = themeExpanded,
                        onExpandedChange = { themeExpanded = it },
                        onValueChange = { newTheme ->
                            SettingsRepository.setDarkTheme(newTheme)
                        },
                        displayText = { if (it) "Dunkel" else "Hell" }
                    )

                    // Only show distance presets if available for selected competition type
                    if (selectedCompetitionType.getAvailablePresets().isNotEmpty()) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline
                        )

                        // Standard-Distanz Setting mit Repository
                        DropdownSettingItem(
                            title = "Standard-Distanz",
                            description = "Wähle die beim Start angezeigte Distanz für ${selectedCompetitionType.displayName}",
                            selectedValue = selectedDefaultDistance,
                            options = distanceOptions,
                            expanded = defaultDistanceExpanded,
                            onExpandedChange = { defaultDistanceExpanded = it },
                            onValueChange = { newDistance ->
                                SettingsRepository.setDefaultDistance(newDistance)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSettingItem(
    title: String,
    description: String,
    selectedValue: T,
    options: List<Pair<T, String>>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (T) -> Unit,
    displayText: ((T) -> String)? = null
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = description,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = displayText?.invoke(selectedValue) ?: options.find { it.first == selectedValue }?.second ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.second) },
                        onClick = {
                            onValueChange(option.first)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    description: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2D3436)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF636E72)
            )
        }
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color(0xFF74B9FF),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen()
    }
}
