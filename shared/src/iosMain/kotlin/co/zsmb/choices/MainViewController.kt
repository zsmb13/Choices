package co.zsmb.choices

import androidx.compose.ui.window.ComposeUIViewController
import co.zsmb.choices.data.getDatabaseBuilder
import co.zsmb.choices.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

val graph = createGraphFactory<AppGraph.Factory>()
    .create(getDatabaseBuilder())

fun MainViewController() = ComposeUIViewController {
    App(graph)
}
