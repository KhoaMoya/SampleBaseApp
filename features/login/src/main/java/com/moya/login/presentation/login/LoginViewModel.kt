package com.moya.login.presentation.login

import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.domain.usecase.Login
import com.moya.login.domain.usecase.ValidateInput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: Login,
    private val validateInput: ValidateInput
) : BaseViewModel() {

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is LoginViewEvent.Login -> {
                handleLoginEvent(data = event)
            }
        }
    }

    private fun handleLoginEvent(data: LoginViewEvent.Login) {
        validateLoginInput(data)
        _viewState.value = LoginState.LoggingIn
        login(
            scope = viewModelScope,
            params = Login.Params(
                email = data.email,
                password = data.password
            )
        ) { either ->
            either.fold(::handleFailure, ::handleOnLoginSuccess)
        }
    }

    private fun handleOnLoginSuccess(loggedInInfo: LoggedInInfo) {
        if (loggedInInfo.code != 0) {
            _viewState.value = LoginState.InvalidEmailOrPassword
        } else {
            _viewState.value = LoginState.LoginSuccess
        }
    }

    private fun validateLoginInput(data: LoginViewEvent.Login) {
        validateInput.validateEmail(data.email)
        validateInput.validatePassword(data.password)
    }
}