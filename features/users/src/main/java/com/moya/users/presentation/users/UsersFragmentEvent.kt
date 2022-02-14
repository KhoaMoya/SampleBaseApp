package com.moya.users.presentation.users

import com.moya.common.base.ScreenEvent

sealed class UsersFragmentEvent : ScreenEvent() {
    object FirstLoading : UsersFragmentEvent()
    object RefreshList : UsersFragmentEvent()
    object LoadMoreUsers : UsersFragmentEvent()
}