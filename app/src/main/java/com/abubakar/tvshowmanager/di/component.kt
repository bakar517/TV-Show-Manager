package com.abubakar.tvshowmanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abubakar.baselib.AppConfig
import com.abubakar.networking.NetworkModule
import com.abubakar.tvshowmanager.MainActivity
import dagger.Component
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        InternalModule::class,
        ServiceModule::class,
    ],
    dependencies = [AppConfig::class]

)
interface AppComponent {
    val retainedComponentFactory: RetainedComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(config: AppConfig): AppComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Retained

@Retained
@Subcomponent(
    modules = [
        FragmentModule::class,
        ViewModelModule::class,
        RetainedModule::class,
    ]
)
abstract class RetainedComponent : ViewModel() {

    abstract fun inject(activity: MainActivity)

    @Subcomponent.Factory
    abstract class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = create() as T

        abstract fun create(): RetainedComponent
    }
}