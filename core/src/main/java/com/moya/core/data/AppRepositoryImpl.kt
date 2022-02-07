package com.moya.core.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.data.preferences.AppPreferences
import com.moya.core.data.preferences.PreferencesConstants
import com.moya.core.domain.repositories.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val preferences: AppPreferences
) : AppRepository {

    override fun logged(): Either<Failure, Boolean> {
        return Either.Right(preferences.get(PreferencesConstants.KEY_LOGGED, false))
    }

}