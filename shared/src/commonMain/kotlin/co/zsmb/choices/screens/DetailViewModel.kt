package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.di.SingleViewModelFactory
import co.zsmb.choices.di.ViewModelFactoryKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    dao: RecordDao,
    recordId: Long,
) : ViewModel() {
    val record: StateFlow<Record?> = dao.getById(recordId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @Inject
    @ContributesIntoMap(AppScope::class)
    @ViewModelFactoryKey(DetailViewModel::class)
    class Factory(
        private val dao: RecordDao
    ) : SingleViewModelFactory {
        override fun create(extras: CreationExtras) = DetailViewModel(dao, extras[RecordIdKey]!!)
    }

    object RecordIdKey : CreationExtras.Key<Long>
}
