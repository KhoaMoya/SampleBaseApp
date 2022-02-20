package com.moya.users.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.UserInfo

interface UsersRepository {

    fun getUsersRemotely(page: Int, perPage: Int): Either<Failure, List<UserInfo>>

    fun cacheUsers(list: List<UserInfo>)

    fun getCachedUsers(): Either<Failure, List<UserInfo>>
}