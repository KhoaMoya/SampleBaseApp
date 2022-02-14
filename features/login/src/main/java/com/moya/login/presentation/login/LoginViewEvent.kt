package com.moya.login.presentation.login

import com.moya.common.base.ScreenEvent

sealed class LoginViewEvent : ScreenEvent() {
    class Login(val username: String, val password: String) : LoginViewEvent()
}