package co.zsmb.choices.screens

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.zsmb.choices.data.RecordDao
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import co.zsmb.choices.data.Record
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    dao: RecordDao,
    onBack: () -> Unit,
) {
    val records by remember { dao.getAllAsFlow() }.collectAsStateWithLifecycle(emptyList())
    var menuForId by remember { mutableStateOf<Long?>(null) }
    val scope = rememberCoroutineScope()

    var showMenu by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var exportData by remember { mutableStateOf("") }
    var showImportDialog by remember { mutableStateOf(false) }
    var importData by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Records") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Text("⋮", style = MaterialTheme.typography.titleLarge)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Export All") },
                            onClick = {
                                showMenu = false
                                scope.launch {
                                    val allRecords = dao.getAll()
                                    exportData = Json.encodeToString(allRecords)
                                    showExportDialog = true
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Import All") },
                            onClick = {
                                showMenu = false
                                importData = ""
                                showImportDialog = true
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(records) { rec ->
                val ts = rec.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { menuForId = rec.id }
                        )
                ) {
                    ListItem(
                        headlineContent = {
                            Text(text = rec.comment?.takeIf { it.isNotBlank() } ?: "(no comment)")
                        },
                        supportingContent = {
                            Text(text = "${ts.date} ${ts.time}")
                        },
                        leadingContent = {
                            Text(text = "#${rec.id}")
                        },
                        trailingContent = {
                            val isPositive = rec.score
                            val bg = if (isPositive) Color(0xFF2E7D32) else Color(0xFFC62828)
                            Surface(
                                shape = CircleShape,
                                color = bg,
                            ) {
                                Box(
                                    modifier = Modifier.size(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isPositive) "+1" else "−1",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    DropdownMenu(
                        expanded = menuForId == rec.id,
                        onDismissRequest = { menuForId = null }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                scope.launch { dao.deleteById(rec.id) }
                                menuForId = null
                            }
                        )
                    }
                }
                Divider()
            }
        }
    }

    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Export Records") },
            text = {
                OutlinedTextField(
                    value = exportData,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clipboardManager?.setText(AnnotatedString(exportData))
                        showExportDialog = false
                    }
                ) {
                    Text("Copy")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text("Import Records") },
            text = {
                OutlinedTextField(
                    value = importData,
                    onValueChange = { importData = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Paste JSON here") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            try {
                                val records = Json.decodeFromString<List<Record>>(importData)
                                dao.deleteAll()
                                dao.insertAll(records)
                                showImportDialog = false
                            } catch (e: Exception) {
                                println("Import failed: $e")
                            }
                        }
                    }
                ) {
                    Text("Import")
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
