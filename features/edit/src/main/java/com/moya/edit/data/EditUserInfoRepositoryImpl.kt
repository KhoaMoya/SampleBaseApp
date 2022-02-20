package com.moya.edit.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.domain.model.UserInfo
import com.moya.edit.data.api.ApiEditUserInfo
import com.moya.edit.data.api.mode.ApiEditUserRequest
import com.moya.edit.data.api.mode.ApiEditUserResponse
import com.moya.edit.domain.repositories.EditUserInfoRepository
import javax.inject.Inject


class EditUserInfoRepositoryImpl @Inject constructor(
    private val apiEditUserInfo: ApiEditUserInfo
) : EditUserInfoRepository {

    override fun updateUserInfo(userInfo: UserInfo): Either<Failure, UserInfo> {
        return requestApi(
            call = apiEditUserInfo.updateUserInfo(
                userId = userInfo.id,
                info = ApiEditUserRequest.fromDomainModel(userInfo)
            ),
            transform = { it.toDomainModel() },
            default = ApiEditUserResponse()
        )
    }
}