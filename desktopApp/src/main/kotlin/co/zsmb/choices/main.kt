package co.zsmb.choices

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.zsmb.choices.data.getDatabaseBuilder

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        App(
            dbBuilder = getDatabaseBuilder(),
        )
    }
}