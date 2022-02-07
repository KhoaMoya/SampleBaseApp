package com.moya.login.presentation.registration

import com.moya.common.base.ScreenState

sealed class RegistrationState: ScreenState() {
    object Registering : RegistrationState()
    object RegistrationSuccess : RegistrationState()
    object EmailAddressRegistered : RegistrationState()
}
