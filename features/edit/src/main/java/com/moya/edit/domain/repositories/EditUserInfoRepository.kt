package com.moya.edit.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.UserInfo

interface EditUserInfoRepository {
    fun updateUserInfo(userInfo: UserInfo): Either<Failure, UserInfo>
}