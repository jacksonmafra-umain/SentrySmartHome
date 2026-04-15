package com.umain.sentry.data.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/** Koin aggregator for `:core:data`. The `@ComponentScan` tells the Koin
 *  compiler plugin to pick up every `@Single` / `@Factory` / `@KoinViewModel`
 *  under `com.umain.sentry.data` and generate the wiring for this module. */
@Module
@ComponentScan("com.umain.sentry.data")
class DataModule
