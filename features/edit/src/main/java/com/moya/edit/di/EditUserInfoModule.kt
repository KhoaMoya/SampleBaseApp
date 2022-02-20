package com.moya.edit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moya.common.base.ViewModelFactory
import com.moya.common.base.ViewModelKey
import com.moya.edit.data.EditUserInfoRepositoryImpl
import com.moya.edit.domain.repositories.EditUserInfoRepository
import com.moya.edit.presentation.EditInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoMap

@Module
@DisableInstallInCheck
abstract class EditUserInfoModule {

    @Binds
    @Reusable
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(EditInfoViewModel::class)
    abstract fun bindEditUserInfoViewModel(editInfoViewModel: EditInfoViewModel): ViewModel

    @Binds
    abstract fun provideEditUserInfoRepository(
        editUserInfoRepositoryImpl: EditUserInfoRepositoryImpl
    ): EditUserInfoRepository

}