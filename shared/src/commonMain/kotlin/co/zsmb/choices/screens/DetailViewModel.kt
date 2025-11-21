package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@AssistedInject
class DetailViewModel(
    dao: RecordDao,
    @Assisted private val recordId: Long,
) : ViewModel() {
    val record: StateFlow<Record?> = dao.getById(recordId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @AssistedFactory
    fun interface Factory {
        fun create(recordId: Long): DetailViewModel
    }
}
