package com.moya.login.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.common.usecase.onSuccess
import com.moya.core.data.preferences.AppPreferences
import com.moya.core.data.preferences.PreferencesConstants
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.domain.repositories.LoginRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Login @Inject constructor(
    private val loginRepository: LoginRepository,
    private val preferences: AppPreferences
) : UseCase<LoggedInInfo, Login.Params>() {

    override suspend fun run(params: Params): Either<Failure, LoggedInInfo> {
        return loginRepository.login(
            email = params.email,
            password = params.password
        ).onSuccess {
            if (it.code == 0) { // login success
                preferences.put(PreferencesConstants.KEY_LOGGED, true)
                preferences.putToken(it.token)
            }
        }
    }

    data class Params(
        val email: String,
        val password: String
    )
}