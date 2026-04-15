package com.umain.sentry.feature.devicehub.di

import com.umain.sentry.data.di.DataModule
import com.umain.sentry.feature.devicehub.DeviceHubViewModel
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.Module as KoinModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

@Module(includes = [DataModule::class])
@ComponentScan("com.umain.sentry.feature.devicehub")
class DeviceHubModule

val deviceHubModule: KoinModule = module {
    viewModelOf(::DeviceHubViewModel)
}
