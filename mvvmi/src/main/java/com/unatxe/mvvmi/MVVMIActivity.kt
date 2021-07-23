package com.unatxe.mvvmi

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.unatxe.commons.data.exceptions.Failure
import kotlin.reflect.KClass

abstract class MVVMIActivity<VM : MVVMIViewModel<ViewData>, ViewData: MVVMIData>
    : AppCompatActivity() {

    /**
     * It's required to link a layout's viewBinding to an Activity
     * It's required to ensure the link between activity and layout
     */
    abstract fun inflateMainViewBinding(): ViewBinding

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(getViewModelClass().java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflateMainViewBinding().root)

        //bindingView()

        val rootView = (findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
            .rootView as ViewGroup
        viewDelegate.initViewDelegate(rootView,
        supportFragmentManager)

        viewModel.viewData.loading.observe(this,onLoadingReceiver)
        viewModel.viewData.error.observe(this,onErrorReceiver)
        onModelInitialized(viewModel.viewData)

        setupViews()

        viewModel.onViewInitialized()
    }

    abstract val viewDelegate : MVVMIDelegate

    private val onLoadingReceiver = Observer<ShowLoading> {
        viewDelegate.showLoading(it.show)
    }

    private val onErrorReceiver = Observer<Failure> {
        viewDelegate.processError(it)
    }

    /**
     * Set up DataBindings and ViewBindings in activity after {@link onCreate()}

    abstract fun bindingView() */

    /**
     * Used to create MVVMIViewModel
     */
    abstract fun getViewModelClass() : KClass<VM>

    /**
     * After bindings, delegates and loading/error views has defined.
     */
    abstract fun setupViews()

    abstract fun onModelInitialized(data: ViewData)

}