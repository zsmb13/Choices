package co.zsmb.choices.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import co.zsmb.choices.data.Record
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.di.ViewModelFactoryKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.reflect.KClass

@AssistedInject
class DetailViewModel(
    dao: RecordDao,
    @Assisted private val recordId: Long,
) : ViewModel() {
    val record: StateFlow<Record?> = dao.getById(recordId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @Inject
    @ContributesIntoMap(AppScope::class)
    @ViewModelFactoryKey(DetailViewModel::class)
    class Factory(
        private val dao: RecordDao,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
            return DetailViewModel(dao, extras[RecordIdKey]!!) as T
        }
    }

    object RecordIdKey : CreationExtras.Key<Long>
}
