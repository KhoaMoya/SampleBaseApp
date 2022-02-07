package com.moya.samplebaseapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.moya.common.base.BaseDialog
import com.moya.common.base.setOnSafetyClickListener
import com.moya.samplebaseapp.databinding.DialogMessageBinding

class MessageDialog(context: Context) : BaseDialog(context) {

    private lateinit var binding: DialogMessageBinding
    private var titleId: Int? = null
    private var messageId: Int? = null
    private var message: String? = null
    private var btnText: Int? = null
    private var onDismiss: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogMessageBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        initViews()
        initActions()
    }

    private fun initViews() {
        binding.run {
            titleId?.let {
                tvTitle.isVisible = true
                tvTitle.text = context.getString(it)
            } ?: kotlin.run {
                tvTitle.isVisible = false
            }

            tvMessage.isVisible = messageId != null || message != null
            messageId?.let {
                tvMessage.text = context.getString(it)
            }
            message?.let {
                tvMessage.text = message
            }

            btnText?.let {
                btnOk.text = context.getString(it)
            }
        }
    }

    private fun initActions() {
        binding.btnOk.setOnSafetyClickListener(this) {
            dismiss()
        }
        setOnDismissListener {
            clearViewClickListeners()
            onDismiss?.invoke()
        }
    }

    fun showMessageDialog(
        titleId: Int?,
        messageId: Int?,
        message: String?,
        btnText: Int?,
        cancelable: Boolean,
        onDismiss: (() -> Unit)?
    ) {
        this.titleId = titleId
        this.messageId = messageId
        this.message = message
        this.btnText = btnText
        this.onDismiss = onDismiss
        this.setCancelable(cancelable)

        show()
    }
}