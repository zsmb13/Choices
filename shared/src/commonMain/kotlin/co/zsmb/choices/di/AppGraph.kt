package co.zsmb.choices.di

import androidx.room.RoomDatabase
import co.zsmb.choices.data.AppDatabase
import co.zsmb.choices.data.RecordDao
import co.zsmb.choices.data.configureAndBuild
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

interface AppGraph : ViewModelGraph {
    val metroVmf: MetroViewModelFactory

    @Provides
    @SingleIn(AppScope::class)
    fun provideDatabase(dbBuilder: RoomDatabase.Builder<AppDatabase>): AppDatabase =
        dbBuilder.configureAndBuild()

    @Provides
    @SingleIn(AppScope::class)
    fun provideDao(database: AppDatabase): RecordDao = database.getDao()
}
