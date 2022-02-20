package com.moya.edit.presentation

import com.moya.common.base.ScreenEvent

sealed class EditInfoViewEvent : ScreenEvent() {

    class LoadUserInfo(val userId: Int) : EditInfoViewEvent()

    class UpdateUserInfo(
        val name: String,
        val avatar: String,
        val location: String,
        val bio: String
    ) : EditInfoViewEvent()
}