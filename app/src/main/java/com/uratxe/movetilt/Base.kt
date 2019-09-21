package com.uratxe.movetilt

import android.app.Application
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import android.view.ViewGroup
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.widget.ContentFrameLayout

import androidx.lifecycle.ViewModel





abstract class BaseViewModel<ModelData,ViewModelCommands>(application: Application) : AndroidViewModel(application){

    val liveData : MutableLiveData<BaseLiveData<ModelData>> = MutableLiveData()

    abstract fun onCommandSend(commands : ViewModelCommands)

}




abstract class BaseActivity<ViewModel : BaseViewModel<ModelData,ViewModelCommands>,
        ModelData,ViewModelCommands>() : AppCompatActivity(){


    @LayoutRes abstract fun layoutId(): Int

    val viewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory(application).create(getViewModelClass())
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

    open val viewDelegate : BaseViewDelegate =  MainViewDelegate()

    open fun onDataReceive(liveData: BaseLiveData<ModelData>) {
        when(liveData){
            is BaseLiveData.Error -> viewDelegate.processError(liveData.t)
            is BaseLiveData.TypeData -> onModelReceived(liveData.data)
            is BaseLiveData.Loading -> viewDelegate.showLoading(liveData.loading)
        }
    }

    abstract fun getViewModelClass() : Class<ViewModel>

    abstract fun setupViews()

    abstract fun onModelReceived(data: ModelData)

}

sealed class BaseLiveData<T>{

    class Error<T>(val t : Throwable) : BaseLiveData<T>()

    class Loading<T>(val loading : Boolean) : BaseLiveData<T>()

    data class TypeData<T>(val data : T) : BaseLiveData<T>()
}

sealed class ViewModelIncomingCommands

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))