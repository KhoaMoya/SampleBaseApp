package com.moya.common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import com.moya.common.R

abstract class BaseDialog(context: Context) : Dialog(context),
    SafetyClickListener {

    override var lastViewClickTime: Long = 0L
    override val viewClickListeners: MutableMap<Int, () -> Unit> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun clearViewClickListeners() {
        viewClickListeners.clear()
    }
}