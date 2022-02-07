package com.moya.login.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.data.api.ApiLogin
import com.moya.login.data.api.model.login.ApiLoginRequestBody
import com.moya.login.data.api.model.login.ApiLoginResponse
import com.moya.login.domain.repositories.LoginRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LoginRepositoryImpl @Inject constructor(
    private val apiLogin: ApiLogin
) : LoginRepository {

    override fun login(email: String, password: String): Either<Failure, LoggedInInfo> {
        return requestApi(
            call = apiLogin.login(ApiLoginRequestBody(email, password)),
            transform = { it.toDomain() },
            default = ApiLoginResponse()
        )
    }
}