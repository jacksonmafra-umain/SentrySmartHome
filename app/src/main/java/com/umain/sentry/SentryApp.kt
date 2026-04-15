package com.umain.sentry

import android.app.Application
import com.umain.sentry.data.di.DataModule
import com.umain.sentry.feature.activities.di.ActivitiesModule
import com.umain.sentry.feature.devicehub.di.DeviceHubModule
import com.umain.sentry.feature.home.di.HomeModule
import com.umain.sentry.feature.room.di.RoomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class SentryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@SentryApp)
            modules(
                DataModule().module,
                HomeModule().module,
                RoomModule().module,
                ActivitiesModule().module,
                DeviceHubModule().module,
            )
        }
    }
}
