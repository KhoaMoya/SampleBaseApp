package com.moya.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.moya.common.extensions.hideKeyBoard
import com.moya.common.extensions.showKeyboard
import com.moya.logging.Logger
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    /**
     * Binding is only valid between [onCreate] and [onDestroy].
     * Otherwise, will throw an exception
     * */
    protected val binding: VB
        get() = requireNotNull(_binding)

    @Suppress("UNCHECKED_CAST")
    protected val viewModel: VM by lazy {
        ViewModelProvider(this).get(getGenericType(javaClass) as Class<VM>)
    }

    protected abstract var navHostResId: Int
    val navController by lazy {
        (supportFragmentManager.findFragmentById(navHostResId) as NavHostFragment)
            .navController
    }

    abstract var confirmDialog: BaseDialog?
    abstract var messageDialog: BaseDialog?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(localClassName)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        initViews()
        initActions()
        initObservers()
    }


    override fun onStart() {
        super.onStart()
        Logger.d(localClassName)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(localClassName)
    }

    protected abstract fun initViews()

    open fun initActions() {
        binding.root.setOnClickListener { hideKeyboard() }
    }

    open fun initObservers() {
        viewModel.viewState.observe(this) {
            updateScreenState(it)
        }
    }

    fun showKeyboard(view: View) {
        view.showKeyboard()
    }

    fun hideKeyboard() {
        currentFocus?.hideKeyBoard()
    }

    abstract fun showMessageDialog(
        titleId: Int? = null,
        messageId: Int? = null,
        message: String? = null,
        btnText: Int? = null,
        cancelable: Boolean = true,
        onDismiss: (() -> Unit)? = null
    )

    abstract fun showConfirmDialog(
        titleId: Int? = null,
        messageId: Int? = null,
        message: String? = null,
        cancelable: Boolean = false,
        btnOkText: Int,
        btnOkAction: () -> Unit,
        btnCancelText: Int,
        btnCancelAction: () -> Unit
    )

    abstract fun showLoading()

    abstract fun hideLoading()

    /**
     * Update ui screen depending on the current state
     *
     *  @param state current state of screen
     * */
    abstract fun updateScreenState(state: ScreenState)

    private fun getGenericType(clazz: Class<*>): Class<*> {
        val type = clazz.genericSuperclass
        val paramType = type as ParameterizedType
        return paramType.actualTypeArguments[0] as Class<*>
    }

    override fun onPause() {
        super.onPause()
        Logger.d(localClassName)
    }

    override fun onStop() {
        super.onStop()
        Logger.d(localClassName)
    }

    override fun onRestart() {
        super.onRestart()
        Logger.d(localClassName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(localClassName)
    }
}