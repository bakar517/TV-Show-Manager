package com.abubakar.tvshowmanager.di

import com.abubakar.baselib.AppConfig
import com.abubakar.tvshowmanager.service.ApiService
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InternalModule::class,
        MockServiceModule::class,
    ],
    dependencies = [AppConfig::class]

)
interface TestComponent : AppComponent {

}

@Module
class MockServiceModule() {
    @Provides
    fun apiService(): ApiService = MockApiService()

}

