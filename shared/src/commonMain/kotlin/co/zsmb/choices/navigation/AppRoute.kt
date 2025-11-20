package co.zsmb.choices.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

@Serializable
data object MainRoute : AppRoute

@Serializable
data object ListRoute : AppRoute
