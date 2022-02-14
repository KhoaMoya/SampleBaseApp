package com.moya.samplebaseapp.presentation

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.moya.common.base.BaseActivity
import com.moya.common.base.BaseDialog
import com.moya.common.base.ScreenState
import com.moya.core.AppConstants
import com.moya.samplebaseapp.R
import com.moya.samplebaseapp.databinding.ActivityMainBinding
import com.moya.samplebaseapp.presentation.dialog.MessageDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    override val viewModel: MainActivityViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = {
        ActivityMainBinding.inflate(it)
    }

    override var navHostResId: Int = R.id.navHostFragment
    override var confirmDialog: BaseDialog? = null
    override var messageDialog: BaseDialog? = null

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            topLevelDestinationIds = setOf(
                com.moya.login.R.id.loginFragment,
                com.moya.users.R.id.usersFragment,
                com.moya.login.R.id.registrationFragment
            )
        )
    }

    override fun initViews() {
        setupActionBar()
        setupBottomNav()
    }

    override fun updateScreenState(state: ScreenState) {
        setDestinationGraph((state as MainActivityState).startDestination)
    }

    private fun setDestinationGraph(destination: Int) {
        if (destination == AppConstants.DEFAULT_INT) return
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)

        navGraph.setStartDestination(destination)
        navController.graph = navGraph
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
        hideBottomNavWhenNeed()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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