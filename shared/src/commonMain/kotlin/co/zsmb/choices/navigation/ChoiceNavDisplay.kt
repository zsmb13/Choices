package co.zsmb.choices.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import co.zsmb.choices.screens.ListScreen
import co.zsmb.choices.screens.MainScreen

@Composable
fun ChoiceNavDisplay() {
    val backstack: MutableList<AppRoute> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            mutableStateListOf(MainRoute)
        }

    NavDisplay(
        backStack = backstack,
        entryProvider = entryProvider {
            entry<MainRoute> {
                MainScreen(
                    onShowList = { backstack.add(ListRoute) }
                )
            }
            entry<ListRoute> {
                ListScreen(
                    onBack = { backstack.removeLastOrNull() }
                )
            }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
    )
}