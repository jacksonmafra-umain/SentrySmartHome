package com.umain.sentry.feature.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umain.sentry.data.model.ActivityEvent
import com.umain.sentry.data.repository.SmartHomeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class ActivitiesViewModel(
    repo: SmartHomeRepository,
) : ViewModel() {
    val state: StateFlow<ActivitiesUiState> = repo.activities
        .map { ActivitiesUiState(events = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ActivitiesUiState())
}

data class ActivitiesUiState(
    val events: List<ActivityEvent> = emptyList(),
)
