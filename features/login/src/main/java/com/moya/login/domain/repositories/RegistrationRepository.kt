package com.moya.login.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.LoggedInInfo

interface RegistrationRepository {
    fun register(name: String, email: String, password: String): Either<Failure, LoggedInInfo>
}