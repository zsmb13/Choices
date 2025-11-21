package co.zsmb.choices.di

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

@MapKey(unwrapValue = true)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelFactoryKey(val value: KClass<out ViewModel>)
