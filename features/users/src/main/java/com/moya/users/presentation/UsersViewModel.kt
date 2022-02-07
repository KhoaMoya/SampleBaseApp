package com.moya.users.presentation

import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor() : BaseViewModel() {

    override fun onEvent(event: ScreenEvent) {
    }
}