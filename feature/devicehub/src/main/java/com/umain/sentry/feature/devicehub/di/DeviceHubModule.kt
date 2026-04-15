package com.umain.sentry.feature.devicehub.di

import com.umain.sentry.feature.devicehub.DeviceHubViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val deviceHubModule: Module = module {
    viewModelOf(::DeviceHubViewModel)
}
