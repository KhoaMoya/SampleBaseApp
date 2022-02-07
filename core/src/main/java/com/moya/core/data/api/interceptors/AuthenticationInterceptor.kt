package com.moya.core.data.api.interceptors

import com.moya.core.data.preferences.AppPreferences
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response

@Singleton
class AuthenticationInterceptor @Inject constructor(
    private val preferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = preferences.getToken()
        return chain.proceed(request)
    }
}