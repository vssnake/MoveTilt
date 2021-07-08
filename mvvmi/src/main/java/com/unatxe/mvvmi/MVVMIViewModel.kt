package com.unatxe.mvvmi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.unatxe.commons.utils.runUI

abstract class MVVMIViewModel<ModelData>(application: Application) : AndroidViewModel(application){

    val liveData : MutableLiveData<MVVMILiveData<ModelData>> = MutableLiveData()
    protected val viewData : MutableLiveData<ModelFromViewInterface> = MutableLiveData()

    protected abstract suspend fun onEventFromView(commands : ModelFromViewInterface)

    fun lauchEventFromView(commands : ModelFromViewInterface){
        runUI {
            onEventFromView(commands)
        }
    }

    abstract fun onViewInitialized()

}