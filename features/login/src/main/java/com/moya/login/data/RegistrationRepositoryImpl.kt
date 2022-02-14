package com.moya.login.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.data.api.ApiRegistration
import com.moya.login.data.api.model.registration.ApiRegistrationRequestBody
import com.moya.login.data.api.model.registration.ApiRegistrationResponse
import com.moya.login.domain.repositories.RegistrationRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RegistrationRepositoryImpl @Inject constructor(
    private val apiRegistration: ApiRegistration
) : RegistrationRepository {

    override fun register(
        name: String,
        email: String,
        password: String
    ): Either<Failure, LoggedInInfo> {
        return requestApi(
            call = apiRegistration.register(ApiRegistrationRequestBody(name, email, password)),
            transform = { it.toDomainModel() },
            default = ApiRegistrationResponse()
        )
    }
}