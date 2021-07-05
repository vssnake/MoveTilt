package com.unatxe.mvvmi

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

abstract class MVVMIActivity<ViewModel : MVVMIViewModel<ModelData>,
        ModelData>() : AppCompatActivity(){

    @LayoutRes
    abstract fun layoutId(): Int

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(getViewModelClass().java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        viewDelegate.initViewDelegate(findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup,
        supportFragmentManager)

        observe(viewModel.liveData){
            it?.let {
                onDataReceive(it)
            }
        }

        setupViews()

        viewModel.onViewInitialized()
    }

    abstract val viewDelegate : MVVMIDelegate


    open fun onDataReceive(liveData: MVVMILiveData<ModelData>) {
        when(liveData){
            is MVVMILiveData.Error -> viewDelegate.processError(liveData.failure)
            is MVVMILiveData.TypeData -> onModelReceived(liveData.data)
            is MVVMILiveData.Loading -> viewDelegate.showLoading(liveData.loading)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

    abstract fun onEventModelReceived(data: ModelFromViewInterface)

}