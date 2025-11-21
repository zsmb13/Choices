package co.zsmb.choices

import android.app.Application
import co.zsmb.choices.data.getDatabaseBuilder
import co.zsmb.choices.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

class ChoicesApp : Application() {
    val appGraph by lazy {
        createGraphFactory<AppGraph.Factory>()
            .create(getDatabaseBuilder(this))
    }
}
