package com.moya.instant_feature.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.instant_feature.databinding.FragmentShareBinding

class ShareFragment : BaseFragment<ShareViewModel, FragmentShareBinding>() {

    override val viewModel: ShareViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentShareBinding {
        return FragmentShareBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
    }

    override fun updateScreenState(newState: ScreenState) {
    }
}