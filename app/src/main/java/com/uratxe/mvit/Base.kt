package com.uratxe.mvit

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import android.view.ViewGroup
import kotlin.reflect.KClass


abstract class BaseViewModel<ModelData,ViewEvent,EventModel>(application: Application) : AndroidViewModel(application){

    val liveData : MutableLiveData<BaseLiveData<ModelData,EventModel>> = MutableLiveData()
    val viewData : MutableLiveData<ViewEvent> = MutableLiveData()

    abstract fun onEventFromView(commands : ViewEvent)

}

abstract class BaseActivity<ViewModel : BaseViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel>() : AppCompatActivity(){

    @LayoutRes abstract fun layoutId(): Int

    open val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(getViewModelClass().java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())

        viewDelegate.initViewDelegate(findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup)

        setupViews()

        observe(viewModel.liveData){
            it?.let {
                onDataReceive(it)
            }
        }

    }

    abstract val viewDelegate : BaseViewDelegate


    open fun onDataReceive(liveData: BaseLiveData<ModelData,EventModel>) {
        when(liveData){
            is BaseLiveData.Error -> viewDelegate.processError(liveData.t)
            is BaseLiveData.TypeData -> onModelReceived(liveData.data)
            is BaseLiveData.Loading -> viewDelegate.showLoading(liveData.loading)
            is BaseLiveData.Event2View -> onEventModelReceived(liveData.events)
        }
    }

    abstract fun getViewModelClass() : KClass<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

    abstract fun onEventModelReceived(data: EventModel)

}

interface BaseViewDelegate {

    var context : Context

    fun processError(error: Throwable)
    fun showLoading(boolean: Boolean)
    fun initViewDelegate(view : ViewGroup)
}

sealed class BaseLiveData<Data,EventsModel>{

    class Error<Data,EventsModel>(val t : Throwable) : BaseLiveData<Data,EventsModel>()

    class Loading<Data,EventsModel>(val loading : Boolean) : BaseLiveData<Data,EventsModel>()

    data class TypeData<Data,EventsModel>(val data : Data) : BaseLiveData<Data,EventsModel>()

    class Event2View<Data,EventsModel>(val events : EventsModel) : BaseLiveData<Data,EventsModel>()
}

sealed class ViewModelIncomingCommands

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))