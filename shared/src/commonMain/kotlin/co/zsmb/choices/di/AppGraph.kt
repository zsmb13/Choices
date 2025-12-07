package co.zsmb.choices.di

import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import co.zsmb.choices.data.AppDatabase
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.data.configureAndBuild
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import kotlin.reflect.KClass

interface AppGraph : ViewModelGraph {
    @Provides
    @SingleIn(AppScope::class)
    fun provideMetroViewModelFactory(
         viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
         manualAssistedFactoryProviders: Map<KClass<out ManualViewModelAssistedFactory>, Provider<ManualViewModelAssistedFactory>>,
    ): MetroViewModelFactory = object: MetroViewModelFactory() {
        override val viewModelProviders get() = viewModelProviders
        override val manualAssistedFactoryProviders get() = manualAssistedFactoryProviders
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(dbBuilder: RoomDatabase.Builder<AppDatabase>): AppDatabase =
        dbBuilder.configureAndBuild()

    @Provides
    @SingleIn(AppScope::class)
    fun provideDao(database: AppDatabase): RecordDao = database.getDao()
}
