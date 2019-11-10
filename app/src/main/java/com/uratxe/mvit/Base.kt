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
import kotlin.reflect.KClass


abstract class MVVMIViewModel<ModelData,ViewEvent,EventModel>(application: Application) : AndroidViewModel(application){

    val liveData : MutableLiveData<MVVMILiveData<ModelData,EventModel>> = MutableLiveData()
    val viewData : MutableLiveData<ViewEvent> = MutableLiveData()

    abstract fun onEventFromView(commands : ViewEvent)

    abstract fun onViewInitialized()

}

abstract class MVVMIActivity<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel>() : AppCompatActivity(){

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

    abstract val viewDelegate : MVVMIDelegate


    open fun onDataReceive(liveData: MVVMILiveData<ModelData,EventModel>) {
        when(liveData){
            is MVVMILiveData.Error -> viewDelegate.processError(liveData.t)
            is MVVMILiveData.TypeData -> onModelReceived(liveData.data)
            is MVVMILiveData.Loading -> viewDelegate.showLoading(liveData.loading)
            is MVVMILiveData.Event2View -> onEventModelReceived(liveData.events)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

    abstract fun onEventModelReceived(data: EventModel)

}

abstract class MVVMIFragment<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel>() : Fragment(){

    @LayoutRes abstract fun layoutId(): Int

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(activity!!.application).create(getViewModelClass().java)
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



    abstract val viewDelegate : MVVMIDelegate


    open fun onDataReceive(liveData: MVVMILiveData<ModelData,EventModel>) {
        when(liveData){
            is MVVMILiveData.Error -> viewDelegate.processError(liveData.t)
            is MVVMILiveData.TypeData -> onModelReceived(liveData.data)
            is MVVMILiveData.Loading -> viewDelegate.showLoading(liveData.loading)
            is MVVMILiveData.Event2View -> onEventModelReceived(liveData.events)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

    abstract fun onEventModelReceived(data: EventModel)

}

interface MVVMIDelegate {

    var context : Context

    fun processError(error: Throwable)
    fun showLoading(boolean: Boolean)
    fun initViewDelegate(view : ViewGroup)
}

sealed class MVVMILiveData<Data,EventsModel>{

    class Error<Data,EventsModel>(val t : Throwable) : MVVMILiveData<Data,EventsModel>()

    class Loading<Data,EventsModel>(val loading : Boolean) : MVVMILiveData<Data,EventsModel>()

    data class TypeData<Data,EventsModel>(val data : Data) : MVVMILiveData<Data,EventsModel>()

    class Event2View<Data,EventsModel>(val events : EventsModel) : MVVMILiveData<Data,EventsModel>()
}

sealed class ViewModelIncomingCommands

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))