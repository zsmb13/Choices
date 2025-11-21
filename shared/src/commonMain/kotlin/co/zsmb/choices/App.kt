package co.zsmb.choices

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.room.RoomDatabase
import co.zsmb.choices.data.AppDatabase
import co.zsmb.choices.di.AppGraph
import co.zsmb.choices.di.MetroViewModelFactory
import co.zsmb.choices.navigation.ChoiceNavDisplay
import dev.zacsweers.metro.createGraphFactory

@Composable
fun App(
    dbBuilder: RoomDatabase.Builder<AppDatabase>,
) {
    // TODO Contribute the builder in a nicer way
    val graph = remember {
        createGraphFactory<AppGraph.Factory>().create(dbBuilder)
    }

    MaterialTheme {
        CompositionLocalProvider(LocalViewModelFactory provides graph.viewModelFactory) {
            ChoiceNavDisplay()
        }
    }
}

val LocalViewModelFactory: ProvidableCompositionLocal<MetroViewModelFactory> =
    compositionLocalOf { error("ViewModel factory not set") }
