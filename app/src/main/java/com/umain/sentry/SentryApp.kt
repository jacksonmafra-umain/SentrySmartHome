package com.umain.sentry

import android.app.Application
import com.umain.sentry.data.di.dataModule
import com.umain.sentry.feature.activities.di.activitiesModule
import com.umain.sentry.feature.devicehub.di.deviceHubModule
import com.umain.sentry.feature.home.di.homeModule
import com.umain.sentry.feature.room.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SentryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@SentryApp)
            modules(
                dataModule,
                homeModule,
                roomModule,
                activitiesModule,
                deviceHubModule,
            )
        }
    }
}
