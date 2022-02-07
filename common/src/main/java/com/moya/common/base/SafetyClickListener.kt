package com.moya.common.base

import android.view.View
import com.moya.common.base.SafetyClickListener.Companion.MIN_DELAY_BETWEEN_CLICK

interface SafetyClickListener {

    var lastViewClickTime: Long
    val viewClickListeners: MutableMap<Int, () -> Unit>

    /**
     * This method has to call when views of screen destroy.
     * It will remove all views references
     * */
    fun clearViewClickListeners()

    companion object {
        const val MIN_DELAY_BETWEEN_CLICK = 300L
    }
}


/**
 * Set click listener for this view. Action only is invoked after last click time an interval time is [MIN_DELAY_BETWEEN_CLICK]
 *
 * @param scope Scope which contains all listeners and last click time
 * @param action Action which will be invoked if current time is satisfied
 * */
fun View.setOnSafetyClickListener(scope: SafetyClickListener, action: () -> Unit) {
    scope.viewClickListeners[this.id] = action
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - scope.lastViewClickTime > MIN_DELAY_BETWEEN_CLICK) {
            scope.lastViewClickTime = currentTime
            scope.viewClickListeners[this.id]?.invoke()
        }
    }
}