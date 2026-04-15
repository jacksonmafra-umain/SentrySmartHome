package com.umain.sentry.feature.room.di

import com.umain.sentry.data.di.DataModule
import com.umain.sentry.feature.room.RoomViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.Module as KoinModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@Module(includes = [DataModule::class])
@ComponentScan("com.umain.sentry.feature.room")
class RoomModule

val roomModule: KoinModule = module {
    // SavedStateHandle is auto-provided in Koin's ViewModel scope.
    viewModel<RoomViewModel> { RoomViewModel(get(), get()) }
}
