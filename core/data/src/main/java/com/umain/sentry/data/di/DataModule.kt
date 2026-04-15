package com.umain.sentry.data.di

import com.umain.sentry.data.repository.SmartHomeRepository
import com.umain.sentry.data.repository.SmartHomeRepositoryImpl
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.module.Module as KoinModule
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin aggregator for `:core:data`. `@Module` + `@ComponentScan` let the
 * Koin Compiler Plugin discover `@Singleton SmartHomeRepositoryImpl` and
 * validate every injection site at compile time.
 *
 * The plugin at 1.0.0-RC1 does *not* emit a runtime `.module` extension
 * (unlike the older KSP processor), so the actual runtime registration
 * lives in [dataModule] below — the annotations and the DSL stay in sync
 * by convention.
 */
@Module
@ComponentScan("com.umain.sentry.data")
class DataModule

val dataModule: KoinModule = module {
    single { SmartHomeRepositoryImpl() } bind SmartHomeRepository::class
}
