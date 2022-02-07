package com.moya.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moya.common.livedata.SingleLiveEvent
import com.moya.common.usecase.Failure

abstract class BaseViewModel : ViewModel() {

    protected val _viewState = SingleLiveEvent<ScreenState>()
    val viewState: LiveData<ScreenState> = _viewState

    protected val _failure: SingleLiveEvent<Failure> = SingleLiveEvent()
    val failure: LiveData<Failure> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    abstract fun onEvent(event: ScreenEvent)
}