package co.zsmb.choices.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

@Serializable
data object MainRoute : AppRoute

@Serializable
data object ListRoute : AppRoute

@Serializable
data object CalendarRoute : AppRoute

@Serializable
data class DetailRoute(val id: Long) : AppRoute
