package co.zsmb.choices.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import co.zsmb.choices.LocalAppGraph
import co.zsmb.choices.LocalViewModelFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.MapKey
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@MapKey(unwrapValue = true)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Inject
@ContributesBinding(AppScope::class)
class MetroViewModelFactory(
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val provider = viewModelProviders[modelClass]
            ?: throw IllegalArgumentException("Unknown model class $modelClass")

        return try {
            @Suppress("UNCHECKED_CAST")
            provider() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> metroViewModel(): T =
    viewModel(T::class, factory = LocalViewModelFactory.current)

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
    crossinline factory: AppGraph.() -> VM,
): VM {
    val graph = LocalAppGraph.current
    return viewModel(
        viewModelStoreOwner,
        key,
        factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: KClass<T>,
                    extras: CreationExtras
                ): T = graph.factory() as T
            },
    )
}