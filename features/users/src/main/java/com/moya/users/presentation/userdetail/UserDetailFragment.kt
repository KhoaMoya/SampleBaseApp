package com.moya.users.presentation.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.users.databinding.FragmentUserDetailBinding


class UserDetailFragment : BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentUserDetailBinding {
        return FragmentUserDetailBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {

    }

    override fun updateScreenState(state: ScreenState) {

    }

}