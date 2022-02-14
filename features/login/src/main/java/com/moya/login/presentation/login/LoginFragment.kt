package com.moya.login.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.common.base.setOnSafetyClickListener
import com.moya.login.R
import com.moya.login.databinding.FragmentLoginBinding
import com.moya.login.domain.usecase.InputException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
    }

    override fun initActions() {
        super.initActions()
        binding.run {
            btnLogin.setOnSafetyClickListener(this@LoginFragment) {
                handleOnClickLogin()
            }
            btnRegister.setOnSafetyClickListener(this@LoginFragment) {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
            edtPassword.setOnEditorActionListener { _, action, _ ->
                when (action) {
                    EditorInfo.IME_ACTION_DONE -> handleOnClickLogin()
                }
                false
            }
        }
    }

    private fun handleOnClickLogin() {
        try {
            binding.run {
                val username = edtUsername.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                viewModel.onEvent(LoginViewEvent.Login(username, password))
            }
        } catch (ex: InputException) {
            when (ex) {
                is InputException.InvalidName -> {
                    showDialogInvalidInput(R.string.registration_invalid_name)
                }
                is InputException.InvalidPassword -> {
                    showDialogInvalidInput(R.string.registration_invalid_password)
                }
                else -> {
                }
            }
        }
    }

    private fun showDialogInvalidInput(@StringRes messageId: Int) {
        showMessageDialog(messageId = messageId)
    }

    override fun updateScreenState(newState: ScreenState) {
        hideKeyboard()
        with(newState as LoginState) {
            if (isLogging) showLoading() else hideLoading()
            if (isShowAuthError) showMessageDialog(messageId = R.string.login_invalid_email_or_mail)
            if (isNavigateToHome) navigateToHomeScreen()
        }
    }

    private fun navigateToHomeScreen() {
        val deepLink = NavDeepLinkRequest.Builder
            .fromUri("samplebaseapp://users".toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_login, true)
            .setLaunchSingleTop(true)
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .build()

        findNavController().navigate(deepLink, navOptions)
    }

}