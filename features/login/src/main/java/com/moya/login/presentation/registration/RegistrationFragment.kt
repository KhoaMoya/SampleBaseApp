package com.moya.login.presentation.registration

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
import com.moya.login.databinding.FragmentRegistrationBinding
import com.moya.login.domain.usecase.InputException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
    }

    override fun initActions() {
        super.initActions()
        binding.run {
            btnRegister.setOnSafetyClickListener(
                this@RegistrationFragment, ::handleOnClickRegister
            )
            btnLogin.setOnSafetyClickListener(this@RegistrationFragment) {
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            edtPassword.setOnEditorActionListener { _, action, _ ->
                when (action) {
                    EditorInfo.IME_ACTION_DONE -> handleOnClickRegister()
                }
                false
            }
        }
    }

    private fun handleOnClickRegister() {
        try {
            binding.run {
                val username = edtUserName.text.toString().trim()
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()

                viewModel.onEvent(RegistrationEvent.Register(username, email, password))
            }
        } catch (ex: InputException) {
            when (ex) {
                is InputException.InvalidName -> {
                    showDialogInvalidInput(R.string.registration_invalid_name)
                }
                is InputException.EmptyEmail -> {
                    showDialogInvalidInput(R.string.registration_invalid_empty_email)
                }
                is InputException.InvalidEmail -> {
                    showDialogInvalidInput(R.string.registration_invalid_email)
                }
                is InputException.InvalidPassword -> {
                    showDialogInvalidInput(R.string.registration_invalid_password)
                }
            }
        }
    }

    private fun showDialogInvalidInput(@StringRes messageId: Int) {
        showMessageDialog(messageId = messageId)
    }

    override fun updateScreenState(state: ScreenState) {
        with(state as RegistrationState) {
            if (isRegistering) showLoading() else hideLoading()
            if (isEmailAddressRegisteredError) showMessageDialog(messageId = R.string.registration_email_address_registered)
            if (isSuccessfulRegistration) navigateToHomeScreen()
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
}