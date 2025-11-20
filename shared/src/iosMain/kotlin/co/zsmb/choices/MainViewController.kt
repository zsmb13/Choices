package co.zsmb.choices

import androidx.compose.ui.window.ComposeUIViewController
import co.zsmb.choices.data.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    App(dbBuilder = getDatabaseBuilder())
}
