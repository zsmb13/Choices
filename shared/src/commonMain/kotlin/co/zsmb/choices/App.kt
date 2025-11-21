package co.zsmb.choices

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import co.zsmb.choices.di.AppGraph
import co.zsmb.choices.di.MetroViewModelFactory
import co.zsmb.choices.navigation.ChoiceNavDisplay

@Composable
fun App(appGraph: AppGraph) {
    MaterialTheme {
        CompositionLocalProvider(LocalViewModelFactory provides appGraph.viewModelFactory) {
            ChoiceNavDisplay()
        }
    }
}

val LocalViewModelFactory: ProvidableCompositionLocal<MetroViewModelFactory> =
    compositionLocalOf { error("ViewModel factory not set") }
