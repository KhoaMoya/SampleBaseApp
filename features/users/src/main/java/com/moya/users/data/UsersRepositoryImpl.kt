package com.moya.users.data

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.requestApi
import com.moya.core.data.cache.daos.UserDao
import com.moya.core.data.cache.model.CacheUserInfo
import com.moya.core.domain.model.UserInfo
import com.moya.users.data.api.ApiUsers
import com.moya.users.data.api.model.ApiUsersResponse
import com.moya.users.domain.repositories.UsersRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val apiUser: ApiUsers
) : UsersRepository {

    override fun getUsersRemotely(page: Int, perPage: Int): Either<Failure, List<UserInfo>> {
        return requestApi(
            call = apiUser.getUsers(page, perPage),
            transform = { it.map { apiModel -> apiModel.toDomainModel() } },
            default = emptyList()
        )
    }

    override fun getUserByIdRemotely(userId: Int): Either<Failure, UserInfo> {
        return requestApi(
            call = apiUser.getUserById(userId),
            transform = { it.toDomainModel() },
            default = ApiUsersResponse()
        )
    }

    override fun cacheUsers(list: List<UserInfo>) {
        userDao.cacheUsers(list.map { CacheUserInfo.fromDomain(it) })
    }

    override fun getCachedUsers(): Either<Failure, List<UserInfo>> {
        return Either.Right(userDao.getCachedUsers().map { it.toDomainModel() })
    }
}