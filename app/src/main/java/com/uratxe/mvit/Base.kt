package com.uratxe.mvit

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uratxe.mvit.exception.Failure
import kotlin.reflect.KClass

interface ModelFromViewInterface

abstract class MVVMIViewModel<ModelData>(application: Application) : AndroidViewModel(application){

    val liveData : MutableLiveData<com.unatxe.mvvmi.MVVMILiveData<ModelData>> = MutableLiveData()
    val viewData : MutableLiveData<com.unatxe.mvvmi.ModelFromViewInterface> = MutableLiveData()

    abstract fun onEventFromView(commands : com.unatxe.mvvmi.ModelFromViewInterface)

    abstract fun onViewInitialized()

}

abstract class MVVMIActivity<ViewModel : com.unatxe.mvvmi.MVVMIViewModel<ModelData>,
        ModelData>() : AppCompatActivity(){

    @LayoutRes abstract fun layoutId(): Int

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(getViewModelClass().java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        viewDelegate.initViewDelegate(findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup)

        observe(viewModel.liveData){
            it?.let {
                onDataReceive(it)
            }
        }

        setupViews()

        viewModel.onViewInitialized()
    }

    abstract val viewDelegate : com.unatxe.mvvmi.MVVMIDelegate


    open fun onDataReceive(liveData: com.unatxe.mvvmi.MVVMILiveData<ModelData>) {
        when(liveData){
            is com.unatxe.mvvmi.MVVMILiveData.Error -> viewDelegate.processError(liveData.failure)
            is com.unatxe.mvvmi.MVVMILiveData.TypeData -> onModelReceived(liveData.data)
            is com.unatxe.mvvmi.MVVMILiveData.Loading -> viewDelegate.showLoading(liveData.loading)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

    abstract fun onEventModelReceived(data: com.unatxe.mvvmi.ModelFromViewInterface)

}

abstract class MVVMIFragment<ViewModel : com.unatxe.mvvmi.MVVMIViewModel<ModelData>,
        ModelData>() : Fragment(){

    @LayoutRes abstract fun layoutId(): Int

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

        viewDelegate.initViewDelegate(view as ViewGroup)

        setupViews()

        observe(viewModel.liveData){
            it?.let {
                onDataReceive(it)
            }
        }

        viewModel.onViewInitialized()
    }



    abstract val viewDelegate : com.unatxe.mvvmi.MVVMIDelegate


    open fun onDataReceive(liveData: com.unatxe.mvvmi.MVVMILiveData<ModelData>) {
        when(liveData){
            is com.unatxe.mvvmi.MVVMILiveData.Error -> viewDelegate.processError(liveData.failure)
            is com.unatxe.mvvmi.MVVMILiveData.TypeData -> onModelReceived(liveData.data)
            is com.unatxe.mvvmi.MVVMILiveData.Loading -> viewDelegate.showLoading(liveData.loading)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

}

interface MVVMIDelegate {

    var context : Context

    fun processError(error: Failure)
    fun showLoading(boolean: Boolean)
    fun initViewDelegate(view : ViewGroup)
}

sealed class MVVMILiveData<Data>{

    class Error<Data>(val failure : Failure) : com.unatxe.mvvmi.MVVMILiveData<Data>()

    class Loading<Data>(val loading : Boolean) : com.unatxe.mvvmi.MVVMILiveData<Data>()

    data class TypeData<Data>(val data : Data) : com.unatxe.mvvmi.MVVMILiveData<Data>()

}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))