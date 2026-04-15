package com.umain.sentry.data.model

/** Logical top-level device hanging off the smart hub. */
data class Device(
    val id: String,
    val name: String,
    val roomId: String,
    val state: DeviceState,
)

enum class CurtainPosition { Open, Half, Closed }

enum class ActivityKind { Sound, Animal, Motion, Package }

/** All device-type-specific state is carried on this sealed hierarchy. Keeps
 *  the repository's signature tight — every mutation is `updateDevice(id, ...)`. */
sealed interface DeviceState {
    data class Light(
        val on: Boolean,
        val brightness: Float,      // 0f..1f
        val colorHex: Long,         // packed ARGB, easy to serialize if we wanted
        val timerOn: Boolean,
    ) : DeviceState

    data class Thermostat(
        val targetC: Float,
        val currentC: Float,
        val outsideC: Float,
        val humidity: Float,        // 0f..1f
        val heating: Boolean,
        val cooling: Boolean,
    ) : DeviceState

    data class Curtains(
        val position: CurtainPosition,
        val brightness: Float,      // 0f..1f — room brightness slider on the Curtains screen
        val auto: Boolean,
    ) : DeviceState

    data class Lock(val locked: Boolean, val battery: Int) : DeviceState

    data class Vacuum(val running: Boolean, val minutesLeft: Int, val battery: Int) : DeviceState

    data class LeakDetector(val leaking: Boolean, val battery: Int) : DeviceState

    data class SmokeDetector(val smoke: Boolean, val battery: Int) : DeviceState

    data class Doorbell(val online: Boolean, val streamingLive: Boolean) : DeviceState
}
