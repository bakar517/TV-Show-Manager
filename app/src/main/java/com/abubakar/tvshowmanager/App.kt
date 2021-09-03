package com.abubakar.tvshowmanager

import android.app.Application
import com.abubakar.baselib.AppConfig
import com.abubakar.baselib.BuildInfo
import com.abubakar.baselib.Environment
import com.abubakar.tvshowmanager.di.AppInjector
import timber.log.Timber

open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(createAppConfigs())
    }

    internal fun createAppConfigs() = AppConfig(
        isDebug = BuildConfig.DEBUG,
        env = Environment.PROD(BASE_URL, KEYS),
        buildInfo = BuildInfo(
            version = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE
        )
    )
}