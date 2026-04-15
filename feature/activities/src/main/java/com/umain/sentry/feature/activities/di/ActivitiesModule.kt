package com.umain.sentry.feature.activities.di

import com.umain.sentry.feature.activities.ActivitiesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val activitiesModule: Module = module {
    viewModelOf(::ActivitiesViewModel)
}
