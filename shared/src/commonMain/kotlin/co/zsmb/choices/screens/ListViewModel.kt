package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.di.ViewModelKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Inject
@ContributesIntoMap(AppScope::class)
@ViewModelKey(ListViewModel::class)
class ListViewModel(
    private val dao: RecordDao
) : ViewModel() {
    val records = dao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteRecord(id: Long) {
        viewModelScope.launch {
            dao.deleteById(id)
        }
    }

    suspend fun exportData(): String {
        val allRecords = dao.getAll()
        return Json.encodeToString(allRecords)
    }

    fun importData(json: String) {
        viewModelScope.launch {
            try {
                val records = Json.decodeFromString<List<Record>>(json)
                dao.deleteAll()
                dao.insertAll(records)
            } catch (e: Exception) {
                println("Import failed: $e")
            }
        }
    }
}
