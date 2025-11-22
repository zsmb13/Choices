package co.zsmb.choices.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@Inject
class MetroViewModelFactory(
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
    private val viewModelFactories: Map<KClass<out ViewModel>, Lazy<AssistedViewModelFactory>>,
) : ViewModelProvider.Factory {

    /**
     * Creates fully injected instances of ViewModels that don't require assisted injection
     */
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val provider = viewModelProviders[modelClass]

        @Suppress("UNCHECKED_CAST")
        return try {
            when {
                provider != null -> provider()
                else -> throw RuntimeException("No provider or factory found for $modelClass")
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        } as T
    }

    /**
     * Returns assisted factories that still need to be invoked to produce a ViewModel
     */
    fun <F : AssistedViewModelFactory> getAssistedFactory(
        modelClass: KClass<out ViewModel>,
    ): F {
        @Suppress("UNCHECKED_CAST")
        return try {
            viewModelFactories[modelClass]?.value
        } catch (e: Exception) {
            throw RuntimeException(e)
        } as F
    }
}

interface AssistedViewModelFactory
