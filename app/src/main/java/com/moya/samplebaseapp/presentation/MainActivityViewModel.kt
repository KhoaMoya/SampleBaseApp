package com.moya.samplebaseapp.presentation

import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.common.usecase.UseCase
import com.moya.common.usecase.onSuccess
import com.moya.core.domain.usecase.CheckLoggedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val checkLoggedIn: CheckLoggedIn
) : BaseViewModel() {

    init {
        onEvent(MainActivityEvent.DefineStartDestinationEvent)
    }

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            MainActivityEvent.DefineStartDestinationEvent -> {
                handleDefineStartDestinationEvent()
            }
        }
    }

    private fun handleDefineStartDestinationEvent() {
        checkLoggedIn(
            scope = viewModelScope,
            params = UseCase.None()
        ) { either ->
            either.onSuccess { isLogged ->
                _viewState.value = if (isLogged) {
                    MainActivityState.SetStartDestination(com.moya.users.R.id.nav_users)
                } else {
                    MainActivityState.SetStartDestination(com.moya.login.R.id.nav_login)
                }
            }
        }
    }
}