package com.umain.sentry.feature.home.di

import com.umain.sentry.data.di.DataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/** `includes = [DataModule::class]` exposes the `SmartHomeRepository` binding
 *  to the Koin Compiler Plugin when this module compiles in isolation — without
 *  it, the plugin reports "Missing dependency: SmartHomeRepository". */
@Module(includes = [DataModule::class])
@ComponentScan("com.umain.sentry.feature.home")
class HomeModule
