package co.zsmb.choices.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf


val LocalAppGraph: ProvidableCompositionLocal<AppGraph> =
    compositionLocalOf { error("App graph not set") }

val LocalViewModelFactory: ProvidableCompositionLocal<MetroViewModelFactory> =
    compositionLocalOf { error("ViewModel factory not set") }

@Composable
fun MetroLocalProvider(
    appGraph: AppGraph,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAppGraph provides appGraph,
        LocalViewModelFactory provides appGraph.viewModelFactory,
        content = content,
    )
}
