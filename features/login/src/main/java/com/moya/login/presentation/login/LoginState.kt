package com.moya.login.presentation.login

import com.moya.common.base.ScreenState

data class LoginState(
    val isLogging: Boolean = false,
    val isShowAuthError: Boolean = false,
    val isNavigateToHome: Boolean = false
) : ScreenState() {

    fun toIsLoggingState(): LoginState {
        return copy(
            isLogging = true,
            isShowAuthError = false,
            isNavigateToHome = false
        )
    }

    fun toShowAuthErrorState(): LoginState {
        return copy(
            isLogging = false,
            isShowAuthError = true,
            isNavigateToHome = false,
        )
    }

    fun toLoginSuccessState(): LoginState {
        return copy(
            isLogging = false,
            isShowAuthError = false,
            isNavigateToHome = true,
        )
    }
}
