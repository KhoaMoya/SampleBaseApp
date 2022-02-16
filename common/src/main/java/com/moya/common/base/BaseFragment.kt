package com.moya.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.moya.common.usecase.Failure
import com.moya.logging.Logger

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment(),
    SafetyClickListener {

    override var lastViewClickTime: Long = 0L
    override val viewClickListeners: MutableMap<Int, () -> Unit> = mutableMapOf()

    private var _binding: VB? = null

    /**
     * Binding is only valid between [onCreateView] and [onDestroyView].
     * Otherwise, will throw an exception
     * */
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = requireNotNull(_binding)

    protected abstract val viewModel: VM

    protected var backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleOnClickBack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(javaClass.simpleName)

        activity?.onBackPressedDispatcher?.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d(javaClass.simpleName)

        _binding = inflateViewBinding(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d(javaClass.simpleName)

        initData(arguments)
        initViews()
        initActions()
        initObservers()
    }

    abstract fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): VB

    override fun onStart() {
        super.onStart()
        Logger.d(javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(javaClass.simpleName)
    }

    /**
     * Receive data from which screen open this screen.
     *
     * @param data [Bundle]: Composite received data.
     */
    open fun initData(data: Bundle?) {}

    /**
     * Setup views of activity. ex: setup recycler view, view pager,...
     * */
    abstract fun initViews()

    /**
     * Setup actions for views of activity. ex: onClickListener, onTouchListener, onScrollListener, ...
     * */
    open fun initActions() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    /**
     * Observer data of view model which can change value
     * */
    open fun initObservers() {
        viewModel.failure.observe(viewLifecycleOwner) { failure ->
            cancelLoadingAndRefreshingAnimation()
            when (failure) {
                is Failure.NetworkConnection -> {
                    Toast.makeText(context, "Network unavailable", Toast.LENGTH_SHORT).show()
                }
                is Failure.ServerError -> {
                    showMessageDialog(message = failure.message)
                }
                is Failure.ClientError -> {
                    showMessageDialog(message = "Error! An error occurred. Please try again later.")
                }
                else -> {
                }
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getBaseActivity(): BaseActivity<*, *> {
        return activity as? BaseActivity<*, *>
            ?: throw Exception("Activity of this fragment must be extended from BaseActivity")
    }

    open fun cancelLoadingAndRefreshingAnimation() {
        hideLoading()
    }

    fun showMessageDialog(
        titleId: Int? = null,
        messageId: Int? = null,
        message: String? = null,
        btnText: Int? = null,
        cancelable: Boolean = true,
        onDismiss: (() -> Unit)? = null
    ) {
        getBaseActivity().showMessageDialog(
            titleId, messageId, message, btnText, cancelable, onDismiss
        )
    }

    fun showConfirmDialog(
        titleId: Int? = null,
        messageId: Int? = null,
        message: String? = null,
        cancelable: Boolean = false,
        btnOkText: Int,
        btnOkAction: () -> Unit,
        btnCancelText: Int,
        btnCancelAction: () -> Unit
    ) {
        getBaseActivity().showConfirmDialog(
            titleId,
            messageId,
            message,
            cancelable,
            btnOkText,
            btnOkAction,
            btnCancelText,
            btnCancelAction
        )
    }

    fun showLoading() {
        getBaseActivity().showLoading()
    }

    fun hideLoading() {
        getBaseActivity().hideLoading()
    }

    fun showKeyboard(view: View) {
        getBaseActivity().showKeyboard(view)
    }

    fun hideKeyboard() {
        getBaseActivity().hideKeyboard()
    }

    /**
     * This function to handle on click button back event.
     * Custom this function if you want handle nothing before exit fragment.
     * Remember call OnBackPressedCallback.remove() to remove callback
     * */
    open fun handleOnClickBack() {
        backPressedCallback.remove()
        getBaseActivity().onBackPressed()
    }

    fun <T> setNavigationResult(key: String, value: T) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
    }

    fun <T> removeNavigationResult(key: String): T? =
        findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)


    fun <T> getNavigationResult(key: String): T? =
        findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

    /**
     * Update ui screen by new state
     *
     *  @param state new state of screen
     * */
    abstract fun updateScreenState(newState: ScreenState)

    /**
     * Request single permission
     *
     * @param permission Permission what is need request
     * */
    fun requestSinglePermissions(permission: String) {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onGrantedPermissions(listOf(permission))
            } else {
                onDeniedPermissions(listOf(permission))
            }
        }.launch(permission)
    }

    /**
     * Request multiple permissions
     *
     * @param permissions list permission what are need request
     * */
    fun requestMultiplePermission(permissions: List<String>) {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { resultRequest ->
            val grantedPermissions =
                resultRequest.entries.filter { it.value == true }.map { it.key }
            val deniedPermissions =
                resultRequest.entries.filter { it.value == false }.map { it.key }
            if (grantedPermissions.isNotEmpty()) {
                onGrantedPermissions(grantedPermissions)
            }
            if (deniedPermissions.isNotEmpty()) {
                onDeniedPermissions(deniedPermissions)
            }
        }.launch(permissions.toTypedArray())
    }

    /**
     * This is callback after permissions are granted
     *
     * @param permissions Permissions which are granted
     * */
    open fun onGrantedPermissions(permissions: List<String>) {}

    /**
     * This is callback after permissions are denied
     *
     * @param permissions Permissions which are denied
     * */
    open fun onDeniedPermissions(permissions: List<String>) {}

    override fun clearViewClickListeners() {
        viewClickListeners.clear()
    }

    override fun onPause() {
        super.onPause()
        Logger.d(javaClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        Logger.d(javaClass.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.d(javaClass.simpleName)
        clearViewClickListeners()
        // clean reference of viewBinding because view of fragment is destroyed
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(javaClass.simpleName)
    }

}