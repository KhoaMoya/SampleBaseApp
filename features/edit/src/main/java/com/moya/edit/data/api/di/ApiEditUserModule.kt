package com.moya.edit.data.api.di

import com.moya.edit.data.api.ApiEditUserInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit

@Module
@DisableInstallInCheck
class ApiEditUserModule {
    @Provides
    fun provideApiEditUserInfo(retrofit: Retrofit): ApiEditUserInfo {
        return retrofit.create(ApiEditUserInfo::class.java)
    }
}