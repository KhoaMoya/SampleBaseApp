package com.moya.core.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.data.api.ApiUserInfo
import com.moya.core.data.api.model.ApiUserInfoResponse
import com.moya.core.data.preferences.AppPreferences
import com.moya.core.data.preferences.PreferencesConstants
import com.moya.core.domain.model.UserInfo
import com.moya.core.domain.repositories.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val preferences: AppPreferences,
    private val apiUser: ApiUserInfo
) : AppRepository {

    override fun loggedIn(): Either<Failure, Boolean> {
        return Either.Right(preferences.get(PreferencesConstants.KEY_LOGGED_IN, false))
    }

    override fun getUserByIdRemotely(userId: Int): Either<Failure, UserInfo> {
        return requestApi(
            call = apiUser.getUserById(userId),
            transform = { it.toDomainModel() },
            default = ApiUserInfoResponse()
        )
    }

}