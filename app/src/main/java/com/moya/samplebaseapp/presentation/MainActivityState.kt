package com.moya.samplebaseapp.presentation

import com.moya.common.base.ScreenState

sealed class MainActivityState : ScreenState() {
    class SetStartDestination(val destination: Int) : MainActivityState()
}