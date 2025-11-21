package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zsmb.choices.data.DatabaseSeeder
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.di.ViewModelKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

@Inject
@ContributesIntoMap(AppScope::class)
@ViewModelKey(MainViewModel::class)
class MainViewModel(
    private val dao: RecordDao,
    private val seeder: DatabaseSeeder,
) : ViewModel() {
    val score = dao.score()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun addRecord(score: Boolean, comment: String?) {
        viewModelScope.launch {
            dao.insert(
                Record(
                    score = score,
                    timestamp = Clock.System.now(),
                    comment = comment?.trim()?.ifBlank { null },
                )
            )
        }
    }

    fun generateTestData() {
        viewModelScope.launch {
            seeder.seed()
        }
    }
}
