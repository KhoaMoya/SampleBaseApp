package com.moya.login.data.api.di

import com.moya.login.data.api.ApiRegistration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiRegistrationModule {

    @Provides
    @ViewModelScoped
    fun provideApiRegistration(retrofit: Retrofit): ApiRegistration =
        retrofit.create(ApiRegistration::class.java)

}