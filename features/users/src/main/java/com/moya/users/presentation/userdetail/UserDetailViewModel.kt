package com.moya.users.presentation.userdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.common.base.ScreenState
import com.moya.common.usecase.Failure
import com.moya.core.domain.model.UserInfo
import com.moya.core.domain.usecase.GetUserById
import com.moya.users.presentation.model.UiUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserById: GetUserById
) : BaseViewModel() {

    override val _viewState: MutableLiveData<ScreenState> = MutableLiveData(UserDetailState())

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is UserDetailEvent.LoadUserDetail -> loadUserDetail(event.userId)
        }
    }

    private fun loadUserDetail(userId: Int) {
        _viewState.value = getCurrentState().toLoadingState()
        getUserById(
            scope = viewModelScope,
            params = GetUserById.Params(userId = userId)
        ) { either ->
            either.fold(::handleFailure, ::handleGetUserDetailSuccess)
        }
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
        _viewState.value = getCurrentState().copy(isLoading = false)
    }

    private fun handleGetUserDetailSuccess(userInfo: UserInfo) {
        _viewState.value = getCurrentState().toLoadedDetail(UiUser.fromDomain(userInfo))
    }

    fun getCurrentState(): UserDetailState = _viewState.value as UserDetailState
}