package com.moya.myprofile.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.myprofile.databinding.FragmentMyProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment :
    BaseFragment<MyProfileViewModel, FragmentMyProfileBinding>() {

    override val viewModel: MyProfileViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentMyProfileBinding {
        return FragmentMyProfileBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
    }

    override fun updateScreenState(newState: ScreenState) {

    }

}