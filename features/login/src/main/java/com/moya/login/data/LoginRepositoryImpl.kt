package com.moya.login.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.data.preferences.AppPreferences
import com.moya.core.data.preferences.PreferencesConstants
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.data.api.ApiLogin
import com.moya.login.data.api.model.login.ApiLoginRequestBody
import com.moya.login.data.api.model.login.ApiLoginResponse
import com.moya.login.domain.repositories.LoginRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoginRepositoryImpl @Inject constructor(
    private val apiLogin: ApiLogin,
    private val preferences: AppPreferences
) : LoginRepository {

    override fun login(email: String, password: String): Either<Failure, LoggedInInfo> {
        return requestApi(
            call = apiLogin.login(ApiLoginRequestBody(email, password)),
            transform = { it.toDomainModel() },
            default = ApiLoginResponse()
        )
    }

    override fun saveToken(token: String) {
        preferences.putToken(token)
    }

    override fun saveMyId(id: Int) {
        preferences.putMyId(id)
    }

    override fun setLoggedIn() {
        preferences.put(PreferencesConstants.KEY_LOGGED_IN, true)
    }
}