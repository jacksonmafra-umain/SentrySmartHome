package com.umain.sentry.feature.devicehub.di

import com.umain.sentry.data.di.DataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class])
@ComponentScan("com.umain.sentry.feature.devicehub")
class DeviceHubModule
