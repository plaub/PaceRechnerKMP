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
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    // Use the repository for persistent settings
    val selectedDefaultDistance by SettingsRepository.defaultDistance
    var defaultDistanceExpanded by remember { mutableStateOf(false) }
    
    val distanceOptions = listOf(
        "sprint" to "Sprint",
        "olympic" to "Olympisch",
        "md" to "Mitteldistanz", 
        "ld" to "Langdistanz"
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
                colors = CardDefaults.cardColors(containerColor = Color.White),
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
                        color = Color(0xFF2D3436)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Konfiguriere deine App-Einstellungen.",
                        fontSize = 16.sp,
                        color = Color(0xFF636E72),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Einstellungen
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Standard-Distanz Setting mit Repository
                    DropdownSettingItem(
                        title = "Standard-Distanz",
                        description = "Wähle die beim Start angezeigte Distanz",
                        selectedValue = selectedDefaultDistance,
                        options = distanceOptions,
                        expanded = defaultDistanceExpanded,
                        onExpandedChange = { defaultDistanceExpanded = it },
                        onValueChange = { newDistance ->
                            SettingsRepository.setDefaultDistance(newDistance)
                        }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color(0xFFE0E0E0)
                    )

                    // Theme Setting (Placeholder)
                    SettingItem(
                        title = "Theme",
                        description = "Hell, Dunkel oder System",
                        value = "System"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSettingItem(
    title: String,
    description: String,
    selectedValue: String,
    options: List<Pair<String, String>>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Setting Title and Description
        Column {
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
        
        // Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = options.find { it.first == selectedValue }?.second ?: "Unbekannt",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF74B9FF),
                    unfocusedBorderColor = Color(0xFFDDD6FE),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { (value, label) ->
                    DropdownMenuItem(
                        text = { 
                            Text(
                                text = label,
                                fontWeight = if (value == selectedValue) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        onClick = {
                            onValueChange(value)
                            onExpandedChange(false)
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = if (value == selectedValue) Color(0xFF74B9FF) else Color(0xFF2D3436)
                        )
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
