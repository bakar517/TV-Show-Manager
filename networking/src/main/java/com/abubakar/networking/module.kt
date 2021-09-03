package com.abubakar.networking

import com.abubakar.baselib.AppConfig
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    fun getOkHttpClient(
        appConfig: AppConfig,
        networkInterceptors: NetworkInterceptors
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (appConfig.isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(networkInterceptors)
            .build()
    }


    @Provides
    fun provideApolloClient(
        okHttpClient: OkHttpClient,
        baseUrl: BaseUrl
    ): ApolloClient =
        ApolloClient.builder()
            .serverUrl(baseUrl())
            .okHttpClient(okHttpClient)
            .build()
}
