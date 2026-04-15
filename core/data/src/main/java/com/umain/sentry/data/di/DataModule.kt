package com.umain.sentry.data.di

import com.umain.sentry.data.repository.SmartHomeRepository
import com.umain.sentry.data.repository.SmartHomeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Hand-written Koin module for `:core:data`.
 *
 * The Koin Compiler Plugin (1.0.0-RC1) validates every `get<T>()` /
 * `koinInject<T>()` / `koinViewModel<T>()` call against the declared
 * bindings at compile time. It only recognises the explicit-lambda DSL
 * form (`single<T> { ... }`, `viewModel<T> { ... }`); the shorter
 * `singleOf(::X)` / `viewModelOf(::X)` reflection forms are invisible
 * to the static analyser, which is why we write every binding out in
 * full here and in the feature modules.
 */
val dataModule: Module = module {
    single<SmartHomeRepository> { SmartHomeRepositoryImpl() }
}
