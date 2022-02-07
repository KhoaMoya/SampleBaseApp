package com.moya.login.presentation.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.StringRes
import androidx.core.net.toUri
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
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                viewModel.onEvent(LoginViewEvent.Login(email, password))
            }
        } catch (ex: InputException) {
            when (ex) {
                is InputException.EmptyEmail -> {
                    showDialogInvalidInput(R.string.registration_invalid_empty_email)
                }
                is InputException.InvalidEmail -> {
                    showDialogInvalidInput(R.string.registration_invalid_email)
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

    override fun updateScreenState(state: ScreenState) {
        when (state) {
            is LoginState.LoggingIn -> {
                hideKeyboard()
                showLoading()
            }
            is LoginState.InvalidEmailOrPassword -> {
                hideLoading()
                showMessageDialog(messageId = R.string.login_invalid_email_or_mail)
            }
            is LoginState.LoginSuccess -> {
                hideLoading()
                navigateToHomeScreen()
            }
        }
    }

    private fun navigateToHomeScreen() {
        val deepLink = NavDeepLinkRequest.Builder
            .fromUri("samplebaseapp://users".toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_login, true)
            .build()

        findNavController().navigate(deepLink, navOptions)
    }

    override fun handleOnClickBack() {
        backPressedCallback.remove()
        activity?.finish()
    }

}