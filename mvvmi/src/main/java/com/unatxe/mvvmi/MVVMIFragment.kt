package com.unatxe.mvvmi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

abstract class MVVMIFragment<ViewModel : MVVMIViewModel<ModelData>,
        ModelData>() : Fragment(){

    @LayoutRes
    abstract fun layoutId(): Int

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application).create(getViewModelClass().java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDelegate.initViewDelegate(view as ViewGroup,childFragmentManager)

        setupViews()

        observe(viewModel.liveData, sendLiveData)

        viewModel.onViewInitialized()
    }

    private val sendLiveData = Observer<MVVMILiveData<ModelData>> {
        onDataReceive(it)
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

}