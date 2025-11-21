package co.zsmb.choices.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@Inject
@ContributesBinding(AppScope::class)
class MetroViewModelFactory(
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
    private val viewModelFactories: Map<KClass<out ViewModel>, SingleViewModelFactory>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val provider = viewModelProviders[modelClass]
        val factory = viewModelFactories[modelClass]

        @Suppress("UNCHECKED_CAST")
        return try {
            when {
                provider != null -> provider()
                factory != null -> factory.create(modelClass, extras)
                else -> throw RuntimeException("No provider or factory found for $modelClass")
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        } as T
    }
}

/**
 * A simplified ViewModelFactory interface to implement with factories
 * that will only ever provide a single type of ViewModel and the type
 * is already matched via binding keys.
 */
interface SingleViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        @Suppress("UNCHECKED_CAST")
        return create(extras) as T
    }

    abstract fun create(extras: CreationExtras): ViewModel
}
