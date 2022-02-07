package com.moya.core.di

import com.moya.core.data.AppRepositoryImpl
import com.moya.core.domain.repositories.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideAppRepository(appRepository: AppRepositoryImpl): AppRepository
}