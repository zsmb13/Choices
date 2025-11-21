package co.zsmb.choices

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.zsmb.choices.di.JvmAppGraph
import dev.zacsweers.metro.createGraph

fun main() = application {
    val graph = createGraph<JvmAppGraph>()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Choices",
    ) {
        App(graph)
    }
}
