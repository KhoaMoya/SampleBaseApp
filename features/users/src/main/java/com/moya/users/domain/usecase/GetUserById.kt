package com.moya.users.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.core.domain.model.UserInfo
import com.moya.users.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUserById @Inject constructor(
    private val usersRepository: UsersRepository
) : UseCase<UserInfo, GetUserById.Params>() {

    override suspend fun run(params: Params): Either<Failure, UserInfo> {
        return usersRepository.getUserByIdRemotely(userId = params.userId)
    }

    data class Params(val userId: Int)
}