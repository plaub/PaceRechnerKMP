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
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import de.pierrelaub.pace_rechner.resources.LocalizationManager
import de.pierrelaub.pace_rechner.resources.Language
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    val strings = LocalizedStrings()
    val currentLanguage by LocalizationManager.currentLanguage
    
    // Use the repository for persistent settings
    val selectedDefaultDistance by SettingsRepository.defaultDistance
    val isDarkTheme by SettingsRepository.isDarkTheme
    val selectedCompetitionType by SettingsRepository.competitionType

    var defaultDistanceExpanded by remember { mutableStateOf(false) }
    var themeExpanded by remember { mutableStateOf(false) }
    var competitionTypeExpanded by remember { mutableStateOf(false) }
    var languageExpanded by remember { mutableStateOf(false) }

    // Competition type options
    val competitionTypeOptions = CompetitionType.entries.map { it to getCompetitionTypeDisplayName(it, strings) }

    // Distance options based on selected competition type
    val distanceOptions = selectedCompetitionType.getAvailablePresets().ifEmpty {
        listOf("" to strings.noPresetsAvailable)
    }

    val themeOptions = listOf(
        false to strings.lightTheme,
        true to strings.darkTheme
    )

    val languageOptions = Language.entries.map { it to it.displayName }

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
                        text = strings.settingsTitle,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = strings.configureSettings,
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
                    // Language Setting
                    DropdownSettingItem(
                        title = strings.language,
                        description = strings.chooseLanguage,
                        selectedValue = currentLanguage,
                        options = languageOptions,
                        expanded = languageExpanded,
                        onExpandedChange = { languageExpanded = it },
                        onValueChange = { newLanguage ->
                            LocalizationManager.setLanguage(newLanguage)
                        }
                    )

                    // Competition Type Setting
                    DropdownSettingItem(
                        title = strings.competition,
                        description = strings.chooseCompetition,
                        selectedValue = selectedCompetitionType,
                        options = competitionTypeOptions,
                        expanded = competitionTypeExpanded,
                        onExpandedChange = { competitionTypeExpanded = it },
                        onValueChange = { newCompetitionType ->
                            SettingsRepository.setCompetitionType(newCompetitionType)
                            // Reset default distance when competition type changes
                            if (newCompetitionType.getAvailablePresets().isNotEmpty()) {
                                SettingsRepository.setDefaultDistance(newCompetitionType.getAvailablePresets().first().first)
                            }
                        }
                    )

                    // Default Distance Setting (only show if presets are available)
                    if (selectedCompetitionType.getAvailablePresets().isNotEmpty()) {
                        DropdownSettingItem(
                            title = strings.templates,
                            description = strings.defaultTemplate,
                            selectedValue = selectedDefaultDistance,
                            options = distanceOptions,
                            expanded = defaultDistanceExpanded,
                            onExpandedChange = { defaultDistanceExpanded = it },
                            onValueChange = { newDistance ->
                                SettingsRepository.setDefaultDistance(newDistance)
                            }
                        )
                    }

                    // Theme Setting
                    DropdownSettingItem(
                        title = strings.theme,
                        description = strings.chooseLightDark,
                        selectedValue = isDarkTheme,
                        options = themeOptions,
                        expanded = themeExpanded,
                        onExpandedChange = { themeExpanded = it },
                        onValueChange = { newTheme ->
                            SettingsRepository.setDarkTheme(newTheme)
                        }
                    )
                }
            }
        }
    }
}

private fun getCompetitionTypeDisplayName(competitionType: CompetitionType, strings: de.pierrelaub.pace_rechner.resources.Strings): String {
    return when (competitionType) {
        CompetitionType.Triathlon -> strings.triathlon
        CompetitionType.Duathlon -> strings.duathlon
        CompetitionType.Swimming -> strings.swimming
        CompetitionType.Cycling -> strings.cycling
        CompetitionType.Running -> strings.running
        CompetitionType.Rowing -> strings.rowingCompetition
        CompetitionType.Hiking -> strings.hikingCompetition
        CompetitionType.Walking -> strings.walkingCompetition
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
