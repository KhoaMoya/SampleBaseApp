package com.moya.core.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure

interface AppRepository {

    fun loggedIn(): Either<Failure, Boolean>
}