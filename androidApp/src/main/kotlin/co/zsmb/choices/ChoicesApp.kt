package co.zsmb.choices

import android.app.Application
import co.zsmb.choices.di.AndroidAppGraph
import dev.zacsweers.metro.createGraphFactory

class ChoicesApp : Application() {
    val appGraph by lazy {
        createGraphFactory<AndroidAppGraph.Factory>().create(this)
    }
}
