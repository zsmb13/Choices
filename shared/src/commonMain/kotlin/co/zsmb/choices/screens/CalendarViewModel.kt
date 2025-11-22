package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.di.ViewModelKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class DayData(
    val records: List<Record>,
    val score: Int
)

@Inject
@ContributesIntoMap(AppScope::class)
@ViewModelKey(CalendarViewModel::class)
class CalendarViewModel(
    private val dao: RecordDao,
) : ViewModel() {
    private val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    
    private val _selectedDate = MutableStateFlow<LocalDate>(today)
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()
    
    val dayDataByDate = dao.getAllAsFlow()
        .map { records ->
            records.groupBy { record ->
                record.timestamp.toLocalDateTime(TimeZone.currentSystemDefault()).date
            }.mapValues { (_, dayRecords) ->
                val score = dayRecords.count { it.score } - dayRecords.count { !it.score }
                DayData(records = dayRecords, score = score)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
    
    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }
}
