package com.unatxe.mvvmi

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.runUI

abstract class MVVMIViewModel<ViewData : MVVMIData>(application: Application) : AndroidViewModel(application){

    var viewData: ViewData = this.initViewData()

    fun launchEventFromView(commands : ModelFromViewInterface){
        runUI {
            onEventFromView(commands)
        }
    }

    abstract fun onViewInitialized()
    protected abstract suspend fun onEventFromView(commands: ModelFromViewInterface)
    abstract fun initViewData(): ViewData
}

interface ModelFromViewInterface

open class MVVMIData(
    val loading: MutableLiveData<ShowLoading> = MutableLiveData(ShowLoading(false)),
    val error: MutableLiveData<Failure> = MutableLiveData()
)
data class ShowLoading(
    val show: Boolean,
    @StringRes val message: Int? = null
)