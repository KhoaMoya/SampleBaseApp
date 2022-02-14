package com.moya.users.data.api.di

import com.moya.users.data.api.ApiUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ApiUsersModule {

    @Provides
    @ViewModelScoped
    fun provideApiUsers(retrofit: Retrofit): ApiUsers {
        return retrofit.create(ApiUsers::class.java)
    }
}