package de.pierrelaub.pace_rechner.ui.navigation.voyager

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import de.pierrelaub.pace_rechner.resources.LocalizedStrings
import de.pierrelaub.pace_rechner.ui.screens.PaceRechnerScreen
import de.pierrelaub.pace_rechner.ui.viewmodel.PaceRechnerViewModel

// Neue Tab-Klasse die das ViewModel als Parameter akzeptiert
class PaceRechnerTabWithViewModel(private val viewModel: PaceRechnerViewModel) : Tab {
    @Composable
    override fun Content() {
        PaceRechnerScreen(viewModel = viewModel)
    }

    override val options: TabOptions
        @Composable
        get() {
            val strings = LocalizedStrings()
            return TabOptions(
                index = 0u,
                title = strings.calculator,
                icon = null
            )
        }

    override fun equals(other: Any?): Boolean {
        return other is PaceRechnerTabWithViewModel
    }

    override fun hashCode(): Int {
        return PaceRechnerTabWithViewModel::class.hashCode()
    }
}
