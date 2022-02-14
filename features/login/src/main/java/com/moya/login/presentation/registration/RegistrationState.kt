package com.moya.login.presentation.registration

import com.moya.common.base.ScreenState

data class RegistrationState(
    val isRegistering: Boolean = false,
    val isSuccessfulRegistration: Boolean = false,
    val isEmailAddressRegisteredError: Boolean = false
) : ScreenState() {

    fun toRegisteringState(): RegistrationState {
        return copy(
            isRegistering = true,
            isSuccessfulRegistration = false,
            isEmailAddressRegisteredError = false
        )
    }

    fun toSuccessfulRegistrationState(): RegistrationState {
        return copy(
            isRegistering = false,
            isSuccessfulRegistration = true,
            isEmailAddressRegisteredError = false
        )
    }

    fun toEmailAddressRegisteredErrorState(): RegistrationState {
        return copy(
            isRegistering = false,
            isSuccessfulRegistration = false,
            isEmailAddressRegisteredError = true
        )
    }

}
