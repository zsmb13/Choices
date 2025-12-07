package co.zsmb.choices.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory


@Composable
fun MetroGraphProvider(
    appGraph: AppGraph,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalMetroViewModelFactory provides appGraph.metroViewModelFactory,
        content = content,
    )
}
