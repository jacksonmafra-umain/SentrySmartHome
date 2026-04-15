package com.umain.sentry.data.repository

import com.umain.sentry.data.model.ActivityEvent
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.data.model.Room
import kotlinx.coroutines.flow.StateFlow

/**
 * The single source of truth for the demo. Everything the UI observes lives
 * inside in-memory `MutableStateFlow`s — no network, no persistence. Writes
 * are fire-and-forget.
 */
interface SmartHomeRepository {
    val homeName: String
    val homeOwnerFirstName: String

    val rooms: StateFlow<List<Room>>
    val devices: StateFlow<List<Device>>
    val activities: StateFlow<List<ActivityEvent>>

    fun device(id: String): Device?
    fun roomDevices(roomId: String): List<Device>

    fun updateDevice(id: String, transform: (DeviceState) -> DeviceState)
}
