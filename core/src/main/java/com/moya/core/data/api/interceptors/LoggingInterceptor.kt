package com.moya.core.data.api.interceptors

import com.moya.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

@Singleton
class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        Logger.v(message = "network: $message")
    }

}