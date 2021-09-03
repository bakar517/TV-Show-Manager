package com.abubakar.tvshowmanager.di

import com.abubakar.baselib.ErrorLog
import com.abubakar.baselib.Navigator
import com.abubakar.tvshowmanager.navigation.RealNavigation
import com.abubakar.tvshowmanager.service.ApiService
import com.abubakar.tvshowmanager.service.TVShowService
import com.abubakar.tvshowmanager.util.ErrorLogImp
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
class InternalModule {
    @Provides
    fun provideErrorLog(logImp: ErrorLogImp): ErrorLog = logImp

    @Provides
    fun providesIoDispatchers(dispatchers: RealDispatchers): Dispatchers = dispatchers

}

@Module
class ServiceModule {

    @Provides
    fun getApiService(tvShowService: TVShowService):ApiService = tvShowService
}

@Module
interface RetainedModule {
    @Binds
    fun bindRealNavigator(realNavigation: RealNavigation): Navigator
}