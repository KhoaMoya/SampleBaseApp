package com.moya.users.presentation.userdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
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

        binding.run {

            btnEditInfo.setOnSafetyClickListener(this@UserDetailFragment) {
                currentUserInfo?.id?.let { userId ->
                    findNavController().navigate(
                        UserDetailFragmentDirections.actionUserDetailToEditInfo(
                            userId
                        )
                    )
                }
            }

            btnShare.setOnSafetyClickListener(this@UserDetailFragment) {
                currentUserInfo?.id?.let { userId ->
                    findNavController().navigate(
                        UserDetailFragmentDirections.actionUserDetailToShare(
                            userId
                        )
                    )
                }
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
        binding.btnShare.isVisible = enable
    }

    private fun checkInstalledFeature() {
        var sessionId = 0

        // Creates an instance of SplitInstallManager.
        val splitInstallManager: SplitInstallManager =
            SplitInstallManagerFactory.create(requireContext())

        // Creates a request to install a module.
        val request = SplitInstallRequest
            .newBuilder()
            .addModule("edit")
            .build()

        splitInstallManager.registerListener { state ->
            if (state.sessionId() != sessionId) return@registerListener

            when (state.status()) {
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                }

                SplitInstallSessionStatus.INSTALLED -> {
                }

                SplitInstallSessionStatus.FAILED,
                SplitInstallSessionStatus.CANCELED,
                SplitInstallSessionStatus.CANCELING -> {
                }

                SplitInstallSessionStatus.PENDING -> {
                }

                SplitInstallSessionStatus.DOWNLOADING -> {
                }

                else -> {
                }
            }
        }

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { sId -> sessionId = sId }
            .addOnFailureListener { exception -> /*to do*/ }

        // To cancel a request before it is installed
        splitInstallManager.cancelInstall(sessionId)

        // To check currently installed dynamic feature modules on the device
        val installedModules: Set<String> = splitInstallManager.installedModules // ["edit","share"]

        // To uninstall modules
        splitInstallManager.deferredUninstall(listOf("edit"))
    }

}