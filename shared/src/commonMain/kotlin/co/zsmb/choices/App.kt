package co.zsmb.choices

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.RoomDatabase
import co.zsmb.choices.data.AppDatabase
import co.zsmb.choices.di.AppGraph
import co.zsmb.choices.navigation.ChoiceNavDisplay
import dev.zacsweers.metro.createGraphFactory

@Composable
@Preview
fun App(dbBuilder: RoomDatabase.Builder<AppDatabase>) {
    // TODO move this init to app entry points, inject context and builder
    val graph = remember {
        createGraphFactory<AppGraph.Factory>().create(dbBuilder)
    }

    // TODO inject into a VM
    val dao = remember { graph.recordDao }

    MaterialTheme {
        ChoiceNavDisplay(dao)
    }
}