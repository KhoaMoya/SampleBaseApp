package com.moya.login.presentation.registration

import com.moya.common.base.ScreenEvent

sealed class RegistrationEvent : ScreenEvent() {
    class Register(val name: String, val email: String, val password: String) :
        RegistrationEvent()
}
