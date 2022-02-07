package com.moya.core.data.api.interceptors

import com.moya.common.usecase.NetworkUnavailableException
import com.moya.core.data.api.NetworkStateUtil
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response

@Singleton
class NetworkStateInterceptor @Inject constructor(private val networkStateUtil: NetworkStateUtil) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (networkStateUtil.isNetworkAvailable()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}