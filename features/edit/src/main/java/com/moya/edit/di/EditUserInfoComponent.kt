package com.moya.edit.di

import com.moya.edit.data.api.di.ApiEditUserModule
import com.moya.edit.presentation.EditInfoFragment
import com.moya.samplebaseapp.di.EditUserModuleDependencies
import dagger.Component

@Component(
    dependencies = [EditUserModuleDependencies::class],
    modules = [EditUserInfoModule::class, ApiEditUserModule::class]
)
interface EditUserInfoComponent {

    @Component.Builder
    interface Builder {
        fun moduleDependencies(editUserModuleDependencies: EditUserModuleDependencies): Builder
        fun build(): EditUserInfoComponent
    }

    fun inject(editInfoFragment: EditInfoFragment)
}