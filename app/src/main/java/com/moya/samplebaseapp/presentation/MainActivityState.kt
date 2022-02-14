package com.moya.samplebaseapp.presentation

import com.moya.common.base.ScreenState
import com.moya.core.AppConstants

data class MainActivityState(
    val startDestination: Int = AppConstants.DEFAULT_INT
) : ScreenState() {

    fun newStartDestination(desId: Int): MainActivityState {
        return copy(startDestination = desId)
    }
}