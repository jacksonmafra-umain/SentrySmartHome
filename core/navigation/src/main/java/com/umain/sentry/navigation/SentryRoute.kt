package com.umain.sentry.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe Navigation Compose routes. Every screen the app exposes is
 * modelled as a sealed variant. `@Serializable` enables the Nav-Compose
 * type-safe nav builder (`composable<SentryRoute.Home>`).
 */
sealed interface SentryRoute {
    @Serializable data object Home : SentryRoute
    @Serializable data object Activities : SentryRoute
    @Serializable data object DeviceHub : SentryRoute

    @Serializable
    data class Room(
        val roomId: String,
        val initialPane: RoomPane = RoomPane.Light,
    ) : SentryRoute
}

enum class RoomPane { Light, Thermostat, Curtains }
