package co.zsmb.choices.di

import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.createGraph

@DependencyGraph
interface AppGraph {
  val message: String

  @Provides
  fun provideMessage(): String = "Hello, world!"
}
