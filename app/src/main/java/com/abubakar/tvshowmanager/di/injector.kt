package com.abubakar.tvshowmanager.di

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.abubakar.baselib.AppConfig

object AppInjector {
    lateinit var component: AppComponent

    fun init(configs: AppConfig) {
        component = DaggerAppComponent
            .factory()
            .create(configs)
    }

    fun initWith(arg: AppComponent) {
        component = arg
    }
}

val ComponentActivity.retainedComponent: RetainedComponent
    get() = ViewModelProvider(this, AppInjector.component.retainedComponentFactory).get()