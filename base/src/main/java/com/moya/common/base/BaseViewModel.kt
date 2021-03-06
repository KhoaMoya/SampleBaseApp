package com.moya.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moya.common.livedata.SingleLiveEvent
import com.moya.common.usecase.Failure

abstract class BaseViewModel : ViewModel() {

    protected abstract val _viewState: MutableLiveData<ScreenState>
    val viewState: LiveData<ScreenState>
        get() = _viewState

    protected val _failure: SingleLiveEvent<Failure> = SingleLiveEvent()
    val failure: LiveData<Failure> = _failure

    protected open fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    abstract fun onEvent(event: ScreenEvent)
}