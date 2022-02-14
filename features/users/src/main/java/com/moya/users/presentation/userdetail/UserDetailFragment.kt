package com.moya.users.presentation.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.users.databinding.FragmentUserDetailBinding
import com.moya.users.presentation.model.UiUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment :
    BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    private val args: UserDetailFragmentArgs by navArgs()

    override val viewModel: UserDetailViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentUserDetailBinding {
        return FragmentUserDetailBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
        loadUserDetailIfNeed()
    }

    private fun loadUserDetailIfNeed() {
        if (!viewModel.getCurrentState().isLoadedData) {
            viewModel.onEvent(UserDetailEvent.LoadUserDetail(args.userId))
        }
    }

    override fun updateScreenState(newState: ScreenState) {
        with(newState as UserDetailState) {
            if (isLoading) showLoading() else hideLoading()
            if (userDetail != null) showUserInfo(userDetail)
        }
    }

    private fun showUserInfo(uiUser: UiUser) {
        binding.run {
            tvName.text = uiUser.name
            tvLocation.text = uiUser.location
            tvBio.text = uiUser.bio
        }
    }

}