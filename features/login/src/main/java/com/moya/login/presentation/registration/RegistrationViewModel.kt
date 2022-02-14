package com.moya.login.presentation.registration

import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.domain.usecase.RegisterNewAccount
import com.moya.login.domain.usecase.ValidateInput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerNewAccount: RegisterNewAccount,
    private val validateInput: ValidateInput
) : BaseViewModel() {

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is RegistrationEvent.Register -> {
                handleRegistrationEvent(event)
            }
        }
    }

    private fun handleRegistrationEvent(data: RegistrationEvent.Register) {
        validateRegistrationInput(data)
        _viewState.value = getCurrentState().toRegisteringState()
        registerNewAccount(
            scope = viewModelScope,
            params = RegisterNewAccount.Params(
                name = data.name,
                email = data.email,
                password = data.password
            )
        ) { either ->
            either.fold(::handleFailure, ::handleRegisterSuccess)
        }
    }

    private fun validateRegistrationInput(data: RegistrationEvent.Register) {
        validateInput.validateUserName(data.name)
        validateInput.validateEmail(data.email)
        validateInput.validatePassword(data.password)
    }

    private fun handleRegisterSuccess(loggedInInfo: LoggedInInfo) {
//        if (loggedInInfo.code != 0) {
//            _viewState.value = getCurrentState().toEmailAddressRegisteredErrorState()
//        } else {
        _viewState.value = getCurrentState().toSuccessfulRegistrationState()
//        }
    }

    private fun getCurrentState(): RegistrationState {
        return (_viewState.value as? RegistrationState) ?: RegistrationState()
    }

}