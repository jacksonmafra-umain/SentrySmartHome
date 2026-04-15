package com.umain.sentry.feature.room.di

import com.umain.sentry.feature.room.RoomViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val roomModule: Module = module {
    // SavedStateHandle is auto-provided by Koin's viewModel scope, so the
    // second `get()` resolves it at creation time.
    viewModel<RoomViewModel> { RoomViewModel(get(), get()) }
}
