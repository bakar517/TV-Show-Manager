package com.abubakar.tvshowmanager

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.abubakar.tvshowmanager.di.AppInjector
import com.abubakar.tvshowmanager.di.DaggerTestComponent

class EspressoTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application = super.newApplication(cl, TestApp::class.java.name, context)
}

class TestApp : App() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.initWith(
            DaggerTestComponent
                .builder()
                .appConfig(createAppConfigs())
                .build()
        )
    }
}