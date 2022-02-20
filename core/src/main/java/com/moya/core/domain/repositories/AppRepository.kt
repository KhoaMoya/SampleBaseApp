package com.moya.core.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.UserInfo

interface AppRepository {

    fun loggedIn(): Either<Failure, Boolean>

    fun getUserByIdRemotely(userId: Int): Either<Failure, UserInfo>
}