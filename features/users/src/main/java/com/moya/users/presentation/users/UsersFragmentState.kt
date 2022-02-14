package com.moya.users.presentation.users

import com.moya.common.base.Pagination
import com.moya.common.base.ScreenState
import com.moya.core.domain.model.UserInfo
import com.moya.users.presentation.model.UiUser

data class UsersFragmentState(
    var isLoading: Boolean = false,
    var isRefreshing: Boolean = false,
    var isLoadingMore: Boolean = false,
    var isEmpty: Boolean = false,
    var isHasNext: Boolean = false,
    var users: List<UiUser> = emptyList(),
    var currentPage: Int = 1
) : ScreenState() {

    fun toLoadingState(): UsersFragmentState {
        return this.copy(
            isLoading = true,
            isRefreshing = false,
            isLoadingMore = false
        )
    }

    fun toLoadingMoreState(): UsersFragmentState {
        return this.copy(
            isLoading = false,
            isRefreshing = false,
            isLoadingMore = true
        )
    }

    fun toRefreshingState(): UsersFragmentState {
        return this.copy(
            isLoading = false,
            isRefreshing = true,
            isLoadingMore = false
        )
    }

    fun toLoadedState(list: List<UserInfo>): UsersFragmentState {
        val uiUsers = list.map { UiUser.fromDomain(it) }
        val newUsers = if (isRefreshing || isLoading) uiUsers else (users + uiUsers)
        return this.copy(
            isLoading = false,
            isRefreshing = false,
            isEmpty = newUsers.isEmpty(),
            isHasNext = list.size == Pagination.DEFAULT_PAGE_SIZE,
            users = newUsers
        )
    }

}