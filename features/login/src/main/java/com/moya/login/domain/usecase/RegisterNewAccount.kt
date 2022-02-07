package com.moya.login.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.common.usecase.onSuccess
import com.moya.core.data.preferences.AppPreferences
import com.moya.core.data.preferences.PreferencesConstants
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.domain.repositories.RegistrationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RegisterNewAccount @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val preferences: AppPreferences
) : UseCase<LoggedInInfo, RegisterNewAccount.Params>() {

    override suspend fun run(params: Params): Either<Failure, LoggedInInfo> {
        return registrationRepository.register(
            name = params.name,
            email = params.email,
            password = params.password
        ).onSuccess {
            if (it.code == 0) { // registration success
                preferences.put(PreferencesConstants.KEY_LOGGED, true)
                preferences.putToken(it.token)
            }
        }
    }

    data class Params(
        val name: String,
        val email: String,
        val password: String
    )
}