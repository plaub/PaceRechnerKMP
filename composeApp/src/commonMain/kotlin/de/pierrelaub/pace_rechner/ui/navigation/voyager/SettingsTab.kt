package de.pierrelaub.pace_rechner.ui.navigation.voyager

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import de.pierrelaub.pace_rechner.ui.screens.SettingsScreen

object SettingsTab : Tab {
    @Composable
    override fun Content() {
        SettingsScreen()
    }

    override val options: TabOptions
        @Composable
        get() {
            val strings = LocalizedStrings()
            return TabOptions(
                index = 2u,
                title = strings.settings,
                icon = null
            )
        }
}
