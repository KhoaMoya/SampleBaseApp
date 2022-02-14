package com.moya.login.domain.repositories

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.LoggedInInfo

interface LoginRepository {
    fun login(email: String, password: String): Either<Failure, LoggedInInfo>
    fun saveToken(token: String)
    fun saveMyId(id: Int)
    fun setLoggedIn()
}