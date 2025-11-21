package co.zsmb.choices

import androidx.compose.ui.window.ComposeUIViewController
import co.zsmb.choices.di.IosAppGraph
import dev.zacsweers.metro.createGraph

val graph = createGraph<IosAppGraph>()

fun MainViewController() = ComposeUIViewController {
    App(graph)
}
