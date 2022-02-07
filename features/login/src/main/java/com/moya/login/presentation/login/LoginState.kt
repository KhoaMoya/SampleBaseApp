package com.moya.login.presentation.login

import com.moya.common.base.ScreenState

sealed class LoginState : ScreenState() {
    object LoggingIn : LoginState()
    object InvalidEmailOrPassword : LoginState()
    object LoginSuccess : LoginState()
}
