package com.moya.common.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children

fun View.setAllEnabled(enabled: Boolean) {
    isEnabled = enabled
    if (this is ViewGroup) children.forEach { child -> child.setAllEnabled(enabled) }
}

fun View.hideKeyBoard() {
    getInputMethodManager()?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    getInputMethodManager()?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.getInputMethodManager(): InputMethodManager? {
    return (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
}