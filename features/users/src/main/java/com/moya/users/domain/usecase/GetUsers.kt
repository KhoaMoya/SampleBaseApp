package com.moya.users.domain.usecase

import com.moya.common.usecase.Either
import com.moya.common.usecase.Failure
import com.moya.common.usecase.getRightOrElse
import com.moya.common.usecase.onSuccess
import com.moya.core.domain.model.UserInfo
import com.moya.users.domain.repositories.UsersRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUsers @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(params: Params): Flow<Either<Failure, List<UserInfo>>> = flow {
        if (params.loadCache) {
            // oad users from cache
            val cachedUsersEither = usersRepository.getCachedUsers()
            if (cachedUsersEither.isRight) {
                val cachedUsers = cachedUsersEither.getRightOrElse(emptyList())
                if (cachedUsers.isNotEmpty()) {
                    // emit if cached users is not empty
                    emit(Either.Right(cachedUsers))
                }
            }
        }
        // load and emit users from api
        val usersFromServer =
            usersRepository.getUsersRemotely(page = params.page, perPage = params.perPage)
                .onSuccess {
                    // cache if is first page
                    usersRepository.cacheUsers(it)
                }
        emit(usersFromServer)
    }.flowOn(Dispatchers.IO)

    data class Params(val page: Int, val perPage: Int, val loadCache: Boolean)
}