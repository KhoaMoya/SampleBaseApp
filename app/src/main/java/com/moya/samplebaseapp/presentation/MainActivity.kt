package com.moya.samplebaseapp.presentation

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.navigation.ui.setupWithNavController
import com.moya.common.base.BaseActivity
import com.moya.common.base.BaseDialog
import com.moya.common.base.ScreenState
import com.moya.samplebaseapp.R
import com.moya.samplebaseapp.databinding.ActivityMainBinding
import com.moya.samplebaseapp.presentation.dialog.MessageDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = {
        ActivityMainBinding.inflate(it)
    }

    override var navHostResId: Int = R.id.navHostFragment
    override var confirmDialog: BaseDialog? = null
    override var messageDialog: BaseDialog? = null

    override fun initViews() {
        setupBottomNav()
        triggerStartDestination()
    }

    override fun initObservers() {
        super.initObservers()

        viewModel.viewState.observe(this) {
            updateScreenState(it)
        }
    }

    private fun triggerStartDestination() {
        viewModel.onEvent(MainActivityEvent.DefineStartDestinationEvent)
    }

    override fun updateScreenState(state: ScreenState) {
        when (state) {
            is MainActivityState.SetStartDestination -> {
                setDestinationGraph(state.destination)
            }
        }
    }

    private fun setDestinationGraph(destination: Int) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)

        navGraph.startDestination = destination
        navController.graph = navGraph
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
        hideBottomNavWhenNeed()
    }

    private fun hideBottomNavWhenNeed() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.moya.users.R.id.usersFragment, com.moya.myprofile.R.id.myProfileFragment -> {
                    binding.bottomNavigation.isVisible = true
                }
                else -> binding.bottomNavigation.isVisible = false
            }
        }
    }

    override fun showMessageDialog(
        titleId: Int?,
        messageId: Int?,
        message: String?,
        btnText: Int?,
        cancelable: Boolean,
        onDismiss: (() -> Unit)?
    ) {
        if (messageDialog?.isShowing != true) {
            messageDialog = MessageDialog(this)
            (messageDialog as? MessageDialog)?.showMessageDialog(
                titleId,
                messageId,
                message,
                btnText,
                cancelable,
                onDismiss
            )
        }
    }

    override fun showConfirmDialog(
        titleId: Int?,
        messageId: Int?,
        message: String?,
        cancelable: Boolean,
        btnOkText: Int,
        btnOkAction: () -> Unit,
        btnCancelText: Int,
        btnCancelAction: () -> Unit
    ) {
    }

    override fun showLoading() {
        binding.viewLoading.isVisible = true
    }

    override fun hideLoading() {
        binding.viewLoading.isVisible = false
    }
}