package com.moya.myprofile.presentation

import androidx.lifecycle.MutableLiveData
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.common.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor() : BaseViewModel() {

    override val _viewState: MutableLiveData<ScreenState> = MutableLiveData(MyProfileState())

    override fun onEvent(event: ScreenEvent) {
    }
}