package com.umain.sentry.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umain.sentry.data.model.ActivityEvent
import com.umain.sentry.data.model.Device
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.data.model.Room
import com.umain.sentry.data.repository.SmartHomeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repo: SmartHomeRepository,
) : ViewModel() {

    val state: StateFlow<HomeUiState> =
        combine(repo.rooms, repo.devices, repo.activities) { rooms, devices, activities ->
            HomeUiState(
                homeName = repo.homeName,
                ownerFirstName = repo.homeOwnerFirstName,
                rooms = rooms,
                selectedRoomId = rooms.firstOrNull()?.id ?: "",
                devices = devices,
                unreadActivities = activities.count { it.live },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    fun selectRoom(roomId: String) {
        // For the demo the selected room tab is UI state only — we could persist
        // it on the repository, but keeping it here keeps the ViewModel small.
        _selectedRoomId = roomId
    }

    fun toggleDevice(id: String) {
        repo.updateDevice(id) { current ->
            when (current) {
                is DeviceState.Light       -> current.copy(on = !current.on)
                is DeviceState.Lock        -> current.copy(locked = !current.locked)
                is DeviceState.Vacuum      -> current.copy(running = !current.running)
                is DeviceState.Thermostat  -> current.copy(heating = !current.heating)
                is DeviceState.Curtains    -> current.copy(auto = !current.auto)
                is DeviceState.LeakDetector,
                is DeviceState.SmokeDetector,
                is DeviceState.Doorbell -> current
            }
        }
    }

    @Suppress("unused") // retained in case we move this to a StateFlow later
    private var _selectedRoomId: String = ""
}

data class HomeUiState(
    val homeName: String = "",
    val ownerFirstName: String = "",
    val rooms: List<Room> = emptyList(),
    val selectedRoomId: String = "",
    val devices: List<Device> = emptyList(),
    val unreadActivities: Int = 0,
    val recentActivities: List<ActivityEvent> = emptyList(),
)
