package co.zsmb.choices.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.reflect.KClass
import kotlin.reflect.cast

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
): VM = viewModel(
    viewModelStoreOwner = viewModelStoreOwner,
    factory = LocalAppGraph.current.viewModelFactory,
)

@Composable
inline fun <reified VM : ViewModel, reified VMAF : AssistedViewModelFactory> assistedMetroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    crossinline buildViewModel: VMAF.() -> VM,
): VM {
    val factory = LocalAppGraph.current.viewModelFactory
    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: KClass<T>,
                    extras: CreationExtras
                ): T = modelClass.cast(
                    factory.getAssistedFactory<VMAF>(modelClass).buildViewModel()
                )
            },
    )
}
