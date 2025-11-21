package co.zsmb.choices

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import co.zsmb.choices.di.AppGraph
import co.zsmb.choices.di.MetroLocalProvider
import co.zsmb.choices.navigation.ChoiceNavDisplay

@Composable
fun App(appGraph: AppGraph) {
    MetroLocalProvider(appGraph) {
        MaterialTheme {
            ChoiceNavDisplay()
        }
    }
}
