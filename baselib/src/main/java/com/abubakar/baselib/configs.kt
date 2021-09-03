package com.abubakar.baselib

class AppConfig(
    val isDebug: Boolean,
    val env: Environment,
    val buildInfo: BuildInfo,
)

data class BuildInfo(
    val version: String,
    val versionCode: Int
)

sealed class Environment(val baseUrl: String, val keysMap: Map<String, String>) {
    data class STAGING(val url: String, val keys: Map<String, String>) : Environment(url, keys)
    data class PROD(val url: String, val keys: Map<String, String>) : Environment(url, keys)
}
