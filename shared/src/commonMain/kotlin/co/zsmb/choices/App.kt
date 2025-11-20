package co.zsmb.choices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.RoomDatabase
import co.zsmb.choices.data.AppDatabase
import co.zsmb.choices.data.Record
import co.zsmb.choices.di.AppGraph
import dev.zacsweers.metro.createGraphFactory
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.time.Clock

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
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val flow = remember { dao.getAllAsFlow() }
            val records by flow.collectAsStateWithLifecycle(emptyList())
            val score by remember { dao.score() }.collectAsStateWithLifecycle(-1)

            val scope = rememberCoroutineScope()

            Text("Score: $score")

            Button(onClick = {
                scope.launch {
                    dao.insert(
                        Record(
                            score = Random.nextBoolean(),
                            timestamp = Clock.System.now(),
                        )
                    )
                }
            }) {
                Text("Add item")
            }

            Button(onClick = { scope.launch { dao.deleteAll() } }) {
                Text("Delete all items")
            }

            LazyColumn {
                items(records) {
                    Text("${it.id} | ${it.score} | ${it.timestamp.toLocalDateTime(TimeZone.UTC)}")
                }
            }
        }
    }
}