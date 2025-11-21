package co.zsmb.choices

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.zsmb.choices.data.getDatabaseBuilder
import co.zsmb.choices.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

fun main() = application {
    val graph = createGraphFactory<AppGraph.Factory>()
        .create(getDatabaseBuilder())

    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        App(graph)
    }
}
