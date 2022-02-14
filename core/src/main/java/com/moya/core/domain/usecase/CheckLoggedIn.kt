package com.moya.core.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.UseCase
import com.moya.core.domain.repositories.AppRepository
import javax.inject.Inject

class CheckLoggedIn @Inject constructor(
    private val appRepository: AppRepository
) : UseCase<Boolean, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, Boolean> {
        return appRepository.loggedIn()
    }
}