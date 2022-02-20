package com.moya.core.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.core.domain.model.UserInfo
import com.moya.core.domain.repositories.AppRepository
import javax.inject.Inject

class GetUserById @Inject constructor(
    private val appRepository: AppRepository
) : UseCase<GetUserById.Params, UserInfo>() {

    override suspend fun run(params: Params): Either<Failure, UserInfo> {
        return appRepository.getUserByIdRemotely(userId = params.userId)
    }

    data class Params(val userId: Int)
}