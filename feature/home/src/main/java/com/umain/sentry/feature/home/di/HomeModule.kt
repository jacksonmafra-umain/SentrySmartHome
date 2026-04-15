package com.umain.sentry.feature.home.di

import com.umain.sentry.feature.home.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule: Module = module {
    viewModelOf(::HomeViewModel)
}
