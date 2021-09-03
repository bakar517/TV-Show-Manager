package com.abubakar.networking

import com.abubakar.baselib.AppConfig
import javax.inject.Inject

class BaseUrl @Inject constructor(
    private val appConfig: AppConfig
) : () -> String {
    override fun invoke() = appConfig.env.baseUrl
}