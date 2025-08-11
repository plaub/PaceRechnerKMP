package de.pierrelaub.pace_rechner.ui.navigation.voyager

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import de.pierrelaub.pace_rechner.ui.screens.HistoryScreen
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

// Neue Tab-Klasse die das ViewModel als Parameter akzeptiert
class HistoryTabWithViewModel(
    private val viewModel: PaceRechnerViewModel,
    private val paceRechnerTab: PaceRechnerTabWithViewModel
) : Tab {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current

        HistoryScreen(
            onLoadCalculation = { calculation ->
                // Lade die Berechnung ins geteilte ViewModel
                viewModel.loadCalculation(calculation)
                // Wechsle zum PaceRechner-Tab (verwende die gleiche Instanz)
                tabNavigator.current = paceRechnerTab
            }
        )
    }

    override val options: TabOptions
        @Composable
        get() {
            val strings = LocalizedStrings()
            return TabOptions(
                index = 1u,
                title = strings.history,
                icon = null
            )
        }

    override fun equals(other: Any?): Boolean {
        return other is HistoryTabWithViewModel
    }

    override fun hashCode(): Int {
        return HistoryTabWithViewModel::class.hashCode()
    }
}
