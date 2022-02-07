package com.moya.login.data.api.di

import com.moya.login.data.api.ApiLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiLoginModel {

    @Provides
    @ViewModelScoped
    fun provideApiLogin(
        retrofit: Retrofit
    ): ApiLogin {
        return retrofit.create(ApiLogin::class.java)
    }
}