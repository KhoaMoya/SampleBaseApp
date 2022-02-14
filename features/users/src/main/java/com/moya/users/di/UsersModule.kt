package com.moya.users.di

import com.moya.users.data.UsersRepositoryImpl
import com.moya.users.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UsersModule {

    @Binds
    @ViewModelScoped
    abstract fun provideUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}