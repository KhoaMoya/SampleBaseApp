package com.moya.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.moya.common.extensions.hideKeyBoard
import java.util.Locale

class SearchEditText constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var onActionTriggered: (query: String) -> Unit = {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setup()
    }

    private fun setup() {
        isFocusable = true
        isFocusableInTouchMode = true
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_SEARCH
            ) {
                handleAction()
                return@setOnEditorActionListener true
            }
            false
        }
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus.not()) {
                clearFocus()
                hideKeyBoard()
            }
        }
        setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                handleAction()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            clearFocus()
            return false
        }
        return super.dispatchKeyEvent(event)
    }

    private fun handleAction() {
        clearFocus()
        if (text.toString().isNotEmpty()) {
            onActionTriggered.invoke(text.toString().lowercase(Locale.getDefault()))
        }
    }

    fun doOnAction(onActionTriggered: (query: String) -> Unit = {}) {
        this.onActionTriggered = onActionTriggered
    }
}