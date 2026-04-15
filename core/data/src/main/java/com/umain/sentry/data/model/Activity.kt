package com.umain.sentry.data.model

data class ActivityEvent(
    val id: String,
    val kinds: List<ActivityKind>,
    val title: String,
    val startedAt: String,   // human-readable for the demo — no need for Instant
    val endedAt: String,
    val thumbnailUrl: String?,
    val live: Boolean,
)
