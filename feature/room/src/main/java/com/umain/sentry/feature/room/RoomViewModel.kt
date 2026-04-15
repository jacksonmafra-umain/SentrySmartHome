package com.umain.sentry.feature.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.umain.sentry.data.model.CurtainPosition
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.data.model.Room
import com.umain.sentry.data.repository.SmartHomeRepository
import com.umain.sentry.navigation.RoomPane
import com.umain.sentry.navigation.SentryRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.KoinViewModel

/**
 * Backs the Bedroom/Living-room detail screen. Derives the room + its light,
 * thermostat and curtains from the repo and exposes a single [state] flow.
 * All writes dispatch through the repository so the home dashboard reflects
 * them instantly.
 */
@KoinViewModel
class RoomViewModel(
    private val repo: SmartHomeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: SentryRoute.Room = savedStateHandle.toRoute<SentryRoute.Room>()

    val state: StateFlow<RoomUiState> =
        combine(repo.rooms, repo.devices) { rooms, devices ->
            val room = rooms.firstOrNull { it.id == args.roomId } ?: return@combine RoomUiState()
            val roomDevices = devices.filter { it.roomId == room.id }
            RoomUiState(
                room = room,
                initialPane = args.initialPane,
                light = roomDevices.firstOrNull { it.state is DeviceState.Light },
                thermostat = roomDevices.firstOrNull { it.state is DeviceState.Thermostat },
                curtains = roomDevices.firstOrNull { it.state is DeviceState.Curtains },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RoomUiState())

    fun setLight(on: Boolean? = null, brightness: Float? = null, colorHex: Long? = null, timerOn: Boolean? = null) {
        val light = state.value.light ?: return
        repo.updateDevice(light.id) { s ->
            (s as DeviceState.Light).copy(
                on = on ?: s.on,
                brightness = brightness ?: s.brightness,
                colorHex = colorHex ?: s.colorHex,
                timerOn = timerOn ?: s.timerOn,
            )
        }
    }

    fun setTargetTemp(valueC: Float) {
        val t = state.value.thermostat ?: return
        repo.updateDevice(t.id) { s -> (s as DeviceState.Thermostat).copy(targetC = valueC) }
    }

    fun toggleHeating() {
        val t = state.value.thermostat ?: return
        repo.updateDevice(t.id) { s ->
            (s as DeviceState.Thermostat).copy(heating = !s.heating, cooling = if (!s.heating) false else s.cooling)
        }
    }

    fun toggleCooling() {
        val t = state.value.thermostat ?: return
        repo.updateDevice(t.id) { s ->
            (s as DeviceState.Thermostat).copy(cooling = !s.cooling, heating = if (!s.cooling) false else s.heating)
        }
    }

    fun setCurtainPosition(pos: CurtainPosition) {
        val c = state.value.curtains ?: return
        repo.updateDevice(c.id) { s -> (s as DeviceState.Curtains).copy(position = pos, auto = false) }
    }

    fun toggleCurtainAuto() {
        val c = state.value.curtains ?: return
        repo.updateDevice(c.id) { s -> (s as DeviceState.Curtains).copy(auto = !s.auto) }
    }

    fun setCurtainBrightness(value: Float) {
        val c = state.value.curtains ?: return
        repo.updateDevice(c.id) { s -> (s as DeviceState.Curtains).copy(brightness = value) }
    }
}

data class RoomUiState(
    val room: Room? = null,
    val initialPane: RoomPane = RoomPane.Light,
    val light: Device? = null,
    val thermostat: Device? = null,
    val curtains: Device? = null,
)
