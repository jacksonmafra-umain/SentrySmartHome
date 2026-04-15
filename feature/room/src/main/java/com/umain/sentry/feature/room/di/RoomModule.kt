package com.umain.sentry.feature.room.di

import com.umain.sentry.feature.room.RoomViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val roomModule: Module = module {
    viewModelOf(::RoomViewModel)
}
