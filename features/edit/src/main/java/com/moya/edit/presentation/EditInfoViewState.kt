package com.moya.edit.presentation

import com.moya.common.base.ScreenState
import com.moya.edit.presentation.model.UiUser

data class EditInfoViewState(
    val isLoading: Boolean = false,
    val isLoadedData: Boolean = false,
    val userInfo: UiUser? = null
) : ScreenState() {

    fun toIsLoading(): EditInfoViewState {
        return this.copy(
            isLoading = true,
            isLoadedData = false
        )
    }

    fun toLoadedUserInfo(uiUser: UiUser): EditInfoViewState {
        return this.copy(
            isLoading = false,
            isLoadedData = true,
            userInfo = uiUser
        )
    }
}