package com.moya.samplebaseapp.di

import com.moya.core.domain.usecase.GetUserById
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@EntryPoint
@InstallIn(SingletonComponent::class)
interface EditUserModuleDependencies {
    fun getRetrofit(): Retrofit
    fun getUserInfoById(): GetUserById
}