package com.moya.users.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.moya.common.base.BaseFragment
import com.moya.common.base.BaseViewModel
import com.moya.common.base.ScreenState
import com.moya.users.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<UsersViewModel, FragmentUsersBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
    }

    override fun updateScreenState(state: ScreenState) {
    }

    override fun handleOnClickBack() {
        backPressedCallback.remove()
        activity?.finish()
    }
}