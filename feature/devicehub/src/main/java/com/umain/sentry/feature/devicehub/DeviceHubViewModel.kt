package com.umain.sentry.feature.devicehub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umain.sentry.data.model.DeviceState
import com.umain.sentry.data.repository.SmartHomeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

/**
 * The Device Hub shows the whole home as one big control — pick the first
 * lamp from the house to drive the hero dimmer.
 */
@KoinViewModel
class DeviceHubViewModel(
    private val repo: SmartHomeRepository,
) : ViewModel() {

    val state: StateFlow<DeviceHubUiState> = repo.devices
        .map { devices ->
            val lamp = devices.firstOrNull { it.state is DeviceState.Light }
            val lampState = lamp?.state as? DeviceState.Light
            DeviceHubUiState(
                lampId = lamp?.id,
                brightness = lampState?.brightness ?: 0.5f,
                on = lampState?.on ?: true,
                colorHex = lampState?.colorHex ?: 0xFFFFD27AL,
                deviceCount = devices.size,
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DeviceHubUiState())

    fun setBrightness(value: Float) {
        val id = state.value.lampId ?: return
        repo.updateDevice(id) { s -> (s as DeviceState.Light).copy(brightness = value) }
    }
}

data class DeviceHubUiState(
    val lampId: String? = null,
    val brightness: Float = 0.5f,
    val on: Boolean = true,
    val colorHex: Long = 0xFFFFD27AL,
    val deviceCount: Int = 0,
)
