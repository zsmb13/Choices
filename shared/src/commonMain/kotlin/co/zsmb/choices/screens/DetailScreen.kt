package co.zsmb.choices.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    recordId: Long,
    onBack: () -> Unit,
) {
    val viewModel = assistedMetroViewModel<DetailViewModel, DetailViewModel.Factory> {
        create(recordId)
    }

    val record by viewModel.record.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            record?.let { rec ->
                Text("Record #${rec.id}", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))

                Text(
                    "Score: ${if (rec.score) "+1" else "-1"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))

                Text("Comment:", style = MaterialTheme.typography.titleMedium)
                Text(rec.comment?.takeIf { it.isNotBlank() } ?: "(no comment)",
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))

                val ts = rec.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                Text("Date: ${ts.date} ${ts.time}", style = MaterialTheme.typography.bodyMedium)
            } ?: Text("Loading...", modifier = Modifier.fillMaxWidth())
        }
    }
}
