package com.moya.login.di

import com.moya.login.data.RegistrationRepositoryImpl
import com.moya.login.domain.repositories.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RegistrationModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRegistrationRepository(
        registrationRepositoryImpl: RegistrationRepositoryImpl
    ): RegistrationRepository
}