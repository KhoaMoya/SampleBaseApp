package com.moya.login.domain.usecase

import android.util.Patterns
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateInput @Inject constructor() {

    fun validateUserName(userName: String): String {
        if (userName.length !in 2..19) {
            throw InputException.InvalidName
        }
        return userName
    }

    fun validateEmail(email: String): String {
        if (email.isEmpty()) {
            throw InputException.EmptyEmail
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw InputException.InvalidEmail
        }
        return email
    }

    fun validatePassword(password: String): String {
        if (password.length !in 8..19) {
            throw InputException.InvalidPassword
        }
        return password
    }
}

sealed class InputException : Exception() {
    object InvalidName : InputException()
    object EmptyEmail : InputException()
    object InvalidEmail : InputException()
    object InvalidPassword : InputException()
}