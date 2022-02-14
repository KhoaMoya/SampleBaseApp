package com.moya.users.presentation.users

import androidx.lifecycle.viewModelScope
import com.moya.common.base.BaseViewModel
import com.moya.common.base.Pagination
import com.moya.common.base.ScreenEvent
import com.moya.core.domain.model.UserInfo
import com.moya.users.domain.usecase.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsers: GetUsers
) : BaseViewModel() {

    init {
        _viewState.value = UsersFragmentState()
        onEvent(UsersFragmentEvent.FirstLoading)
    }

    override fun onEvent(event: ScreenEvent) {
        when (event) {
            is UsersFragmentEvent.FirstLoading -> {
                firstLoadUsers()
            }
            is UsersFragmentEvent.LoadMoreUsers -> {
                loadMoreUsers()
            }
            is UsersFragmentEvent.RefreshList -> {
                refreshList()
            }
        }
    }

    private fun firstLoadUsers() {
        _viewState.value = getCurrentState().toLoadingState()
        viewModelScope.launch {
            getUsers(
                GetUsers.Params(
                    page = 1,
                    perPage = Pagination.DEFAULT_PAGE_SIZE,
                    loadCache = true
                )
            ).collect { either ->
                either.fold(::handleFailure, ::handleLoadUsersSuccess)
            }
        }
    }

    private fun loadMoreUsers() {
        _viewState.value = getCurrentState().toLoadingMoreState()
        viewModelScope.launch {
            getUsers(
                GetUsers.Params(
                    page = getCurrentState().currentPage + 1,
                    perPage = Pagination.DEFAULT_PAGE_SIZE,
                    loadCache = false
                )
            ).collect { either ->
                either.fold(::handleFailure, ::handleLoadUsersSuccess)
            }
        }
    }

    private fun refreshList() {
        _viewState.value = getCurrentState().toRefreshingState()
        viewModelScope.launch {
            getUsers(
                GetUsers.Params(
                    page = 1,
                    perPage = Pagination.DEFAULT_PAGE_SIZE,
                    loadCache = false
                )
            ).collect { either ->
                either.fold(::handleFailure, ::handleLoadUsersSuccess)
            }
        }
    }

    private fun handleLoadUsersSuccess(users: List<UserInfo>) {
        _viewState.value = getCurrentState().toLoadedState(users)
    }

    private fun getCurrentState(): UsersFragmentState {
        return (_viewState.value as? UsersFragmentState) ?: UsersFragmentState()
    }
}