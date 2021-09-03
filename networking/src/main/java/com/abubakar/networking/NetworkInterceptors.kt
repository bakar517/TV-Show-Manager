package com.abubakar.networking

import com.abubakar.baselib.AppConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptors @Inject constructor(
    private val appConfig: AppConfig
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val builder = request.newBuilder()
        for ((key, value) in appConfig.env.keysMap) {
            builder.addHeader(key, value)
        }
        return chain.proceed(builder.build())
    }
}
