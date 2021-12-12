package io.hvam.android.githubapisample.app

import android.app.Application
import io.hvam.android.githubapi.di.githubApiModules
import io.hvam.android.githubapisample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        // Consider differentiating logging of debug, and production builds
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule, githubApiModules)
        }
    }
}
