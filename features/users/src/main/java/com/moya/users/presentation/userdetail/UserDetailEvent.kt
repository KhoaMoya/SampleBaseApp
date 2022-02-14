package com.moya.users.presentation.userdetail

import com.moya.common.base.ScreenEvent

sealed class UserDetailEvent : ScreenEvent() {
    class LoadUserDetail(val userId: Int) : UserDetailEvent()
}