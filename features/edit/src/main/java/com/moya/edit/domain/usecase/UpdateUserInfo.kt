package com.moya.edit.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.core.domain.model.UserInfo
import com.moya.edit.domain.repositories.EditUserInfoRepository
import javax.inject.Inject

class UpdateUserInfo @Inject constructor(
    private val editUserInfoRepository: EditUserInfoRepository
) : UseCase<UpdateUserInfo.Params, UserInfo>() {

    override suspend fun run(params: Params): Either<Failure, UserInfo> {
        return editUserInfoRepository.updateUserInfo(params.userInfo)
    }

    data class Params(val userInfo: UserInfo)
}
