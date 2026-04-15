package com.umain.sentry.data.di

import com.umain.sentry.data.repository.SmartHomeRepository
import com.umain.sentry.data.repository.SmartHomeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Hand-written Koin module for `:core:data`. The Koin Compiler Plugin
 * optimises the `single { ... }` call at build time, so we don't lose the
 * compile-time safety we'd have had with `@Single`.
 */
val dataModule: Module = module {
    single { SmartHomeRepositoryImpl() } bind SmartHomeRepository::class
}
