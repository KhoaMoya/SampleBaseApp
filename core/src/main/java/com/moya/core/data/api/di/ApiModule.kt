package com.moya.core.data.api.di

import com.moya.core.data.api.ApiConstants
import com.moya.core.data.api.interceptors.AuthenticationInterceptor
import com.moya.core.data.api.interceptors.HeaderInterceptor
import com.moya.core.data.api.interceptors.LoggingInterceptor
import com.moya.core.data.api.interceptors.NetworkStateInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkStateInterceptor: NetworkStateInterceptor,
        headerInterceptor: HeaderInterceptor,
        authenticationInterceptor: AuthenticationInterceptor,
        loggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkStateInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(authenticationInterceptor)
            .addInterceptor(HttpLoggingInterceptor(loggingInterceptor).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}