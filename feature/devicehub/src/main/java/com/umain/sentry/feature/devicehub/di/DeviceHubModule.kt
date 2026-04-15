package com.umain.sentry.feature.devicehub.di

import com.umain.sentry.feature.devicehub.DeviceHubViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val deviceHubModule: Module = module {
    viewModel<DeviceHubViewModel> { DeviceHubViewModel(get()) }
}
