package com.moya.edit.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.common.base.ScreenState
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.UserInfo
import com.moya.core.domain.usecase.GetUserById
import com.moya.edit.domain.usecase.UpdateUserInfo
import com.moya.edit.presentation.model.UiUser
import javax.inject.Inject

class EditInfoViewModel @Inject constructor(
    private val updateUserInfo: UpdateUserInfo,
    private val getUserById: GetUserById
) : BaseViewModel() {

    override val _viewState: MutableLiveData<ScreenState> = MutableLiveData(EditInfoViewState())

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is EditInfoViewEvent.LoadUserInfo -> loadUserInfoById(event.userId)
        }
    }

    private fun loadUserInfoById(userId: Int) {
        if (getCurrentState().isLoadedData) return

        _viewState.value = getCurrentState().toIsLoading()

        getUserById(
            scope = viewModelScope,
            params = GetUserById.Params(userId = userId)
        ) { either ->
            either.fold(::handleFailure, ::handleLoadUserInfoSuccess)
        }
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _viewState.value = getCurrentState().copy(isLoading = false)
    }

    private fun handleLoadUserInfoSuccess(userInfo: UserInfo) {
        _viewState.value = getCurrentState().toLoadedUserInfo(UiUser.fromDomain(userInfo))
    }

    private fun getCurrentState(): EditInfoViewState {
        return (_viewState.value as? EditInfoViewState) ?: EditInfoViewState()
    }
}