package com.moya.users.presentation.userdetail

import com.moya.common.base.ScreenState
import com.moya.users.presentation.model.UiUser

data class UserDetailState(
    val isLoading: Boolean = false,
    val isLoadedData: Boolean = false,
    val userId: Int? = null,
    val userDetail: UiUser? = null
) : ScreenState() {

    fun toLoadingState(): UserDetailState {
        return copy(isLoading = true)
    }

    fun toLoadedDetail(uiUser: UiUser): UserDetailState {
        return copy(
            isLoading = false,
            isLoadedData = true,
            userDetail = uiUser
        )
    }
}