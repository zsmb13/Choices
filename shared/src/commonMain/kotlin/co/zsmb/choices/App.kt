package co.zsmb.choices

import androidx.compose.runtime.Composable
import co.zsmb.choices.di.AppGraph
import co.zsmb.choices.di.MetroGraphProvider
import co.zsmb.choices.navigation.ChoiceNavDisplay
import co.zsmb.choices.ui.AppTheme

@Composable
fun App(appGraph: AppGraph) {
    MetroGraphProvider(appGraph) {
        AppTheme {
            ChoiceNavDisplay()
        }
    }
}
