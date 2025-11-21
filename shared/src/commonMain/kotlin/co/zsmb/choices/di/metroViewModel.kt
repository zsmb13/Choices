package co.zsmb.choices.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.reflect.KClass


@Composable
inline fun <reified T : ViewModel> metroViewModel(): T =
    viewModel(factory = LocalViewModelFactory.current)

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    crossinline factory: AppGraph.() -> VM,
): VM {
    val appGraph = LocalAppGraph.current
    return viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras
            ): T = appGraph.factory() as T
        },
    )
}
