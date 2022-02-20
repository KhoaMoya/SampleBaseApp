package com.moya.edit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.common.base.ViewModelFactory
import com.moya.edit.databinding.FragmentEditInfoBinding
import com.moya.edit.di.DaggerEditUserInfoComponent
import com.moya.edit.presentation.model.UiUser
import com.moya.samplebaseapp.di.EditUserModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class EditInfoFragment : BaseFragment<EditInfoViewModel, FragmentEditInfoBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory

    override val viewModel: EditInfoViewModel by viewModels { factory }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentEditInfoBinding {
        return FragmentEditInfoBinding.inflate(inflater, container, attachToParent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerEditUserInfoComponent.builder()
            .moduleDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity(),
                    EditUserModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun initData() {
        val userId = requireArguments().getInt(USER_ID_KEY)
        viewModel.onEvent(EditInfoViewEvent.LoadUserInfo(userId))
    }

    override fun initViews() {
    }

    override fun updateScreenState(newState: ScreenState) {
        with(newState as EditInfoViewState) {
            if (isLoading) showLoading() else hideLoading()
            if (userInfo != null) {
                showUserInfo(userInfo)
            }
        }
    }

    private fun showUserInfo(userInfo: UiUser?) {
        if (userInfo == null) return
        binding.run {
            ivAvatar.setImageResource(com.moya.users.R.drawable.ic_avatar)
            edtName.setText(userInfo.name)
            edtLocation.setText(userInfo.location)
            edtBio.setText(userInfo.bio)
        }
    }

    companion object {
        const val USER_ID_KEY = "userId"
    }
}
