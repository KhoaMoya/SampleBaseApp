package com.moya.instant_feature.presentation

import androidx.lifecycle.MutableLiveData
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenEvent
import com.moya.common.base.ScreenState

class ShareViewModel : BaseViewModel() {

    override val _viewState: MutableLiveData<ScreenState> = MutableLiveData()

    override fun onEvent(event: ScreenEvent) {
    }
}