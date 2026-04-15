package com.umain.sentry.data.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

/**
 * Koin aggregator for `:core:data`. `@ComponentScan` tells the Koin
 * compiler plugin to pick up every `@Singleton` / `@Factory` /
 * `@KoinViewModel` under `com.umain.sentry.data` and generate the wiring
 * for this module at compile time — the `DataModule().module` extension
 * is emitted into `org.koin.ksp.generated`.
 */
@Module
@ComponentScan("com.umain.sentry.data")
class DataModule
