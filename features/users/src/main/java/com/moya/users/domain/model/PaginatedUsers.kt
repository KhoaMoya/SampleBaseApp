package com.moya.users.domain.model

import com.moya.common.base.Pagination
import com.moya.core.domain.model.UserInfo

data class PaginatedUsers(
    val pagination: Pagination,
    val users: List<UserInfo>
)