package com.moya.samplebaseapp.presentation

import com.moya.common.base.ScreenEvent

sealed class MainActivityEvent: ScreenEvent() {
    object DefineStartDestinationEvent : MainActivityEvent()
}