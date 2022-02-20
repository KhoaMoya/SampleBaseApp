package com.moya.users.presentation.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.common.base.setOnSafetyClickListener
import com.moya.users.R
import com.moya.users.databinding.FragmentUserDetailBinding
import com.moya.users.presentation.model.UiUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment :
    BaseFragment<UserDetailViewModel, FragmentUserDetailBinding>() {

    private var currentUserInfo: UiUser? = null

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

    override fun initActions() {
        super.initActions()
        binding.btnEditInfo.setOnSafetyClickListener(this) {
            currentUserInfo?.id?.let { userId ->
                findNavController().navigate(
                    UserDetailFragmentDirections.actionUserDetailToEditInfo(
                        userId
                    )
                )
            }
        }
    }

    private fun loadUserDetailIfNeed() {
        if (!viewModel.getCurrentState().isLoadedData) {
            viewModel.onEvent(UserDetailEvent.LoadUserDetail(args.userId))
        }
    }

    override fun updateScreenState(newState: ScreenState) {
        with(newState as UserDetailState) {
            if (isLoading) showLoading() else hideLoading()
            if (userDetail != null) {
                enableButtonEdit(true)
                showUserInfo(userDetail)
            } else {
                enableButtonEdit(false)
            }
        }
    }

    private fun showUserInfo(uiUser: UiUser) {
        currentUserInfo = uiUser
        binding.run {
            ivAvatar.setImageResource(R.drawable.ic_avatar)
            tvName.text = uiUser.name
            tvLocation.text = uiUser.location
            tvBio.text = uiUser.bio
        }
    }

    private fun enableButtonEdit(enable: Boolean) {
        binding.btnEditInfo.isVisible = enable
    }

}