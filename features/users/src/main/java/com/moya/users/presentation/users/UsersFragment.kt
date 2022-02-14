package com.moya.users.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.moya.common.base.BaseFragment
import com.moya.common.base.ScreenState
import com.moya.users.databinding.FragmentUsersBinding
import com.moya.users.presentation.model.UiUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseFragment<UsersViewModel, FragmentUsersBinding>() {

    private var _currentState: UsersFragmentState = UsersFragmentState()
    private val _userAdapter: UsersAdapter = UsersAdapter(::onClickUser)

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(inflater, container, attachToParent)
    }

    override fun initViews() {
        setupRecyclerView()
        setupRefreshLayout()
    }

    private fun setupRecyclerView() {
        binding.rvUsers.adapter = _userAdapter
    }

    private fun setupRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.onEvent(UsersFragmentEvent.RefreshList)
        }
    }

    private fun onClickUser(userId: Int) {
        val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(userId)
        findNavController().navigate(action)
    }

    override fun updateScreenState(state: ScreenState) {
        with(state as UsersFragmentState) {
            _currentState = this
            binding.run {
                if (isLoading) showLoading() else hideLoading()
                refreshLayout.isRefreshing = isRefreshing
                showEmpty(isEmpty)
                setUsers(users)
            }
        }
    }

    private fun setUsers(users: List<UiUser>) {
        _userAdapter.setUsers(users)
    }

    private fun showEmpty(isShow: Boolean) {

    }

    override fun cancelLoadingAndRefreshingAnimation() {
        super.cancelLoadingAndRefreshingAnimation()
        binding.refreshLayout.isRefreshing = false
    }

}