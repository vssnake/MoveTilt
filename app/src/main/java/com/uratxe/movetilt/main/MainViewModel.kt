package com.uratxe.movetilt.main

import android.app.Application
import com.uratxe.mvit.MVVMILiveData
import com.uratxe.mvit.MVVMIViewModel

class MainViewModel(application: Application) : MVVMIViewModel<MainData, MainViewEvent, MainModelEvent>(application) {

    override fun onEventFromView(commands: MainViewEvent) {

        when (commands){
            is MainViewEvent.RetrieveUserEvent -> retrieveUser()
            is MainViewEvent.ProcessErrorEvent -> {processError(commands.typeError)}
        }
    }

    private fun retrieveUser(){
        liveData.value = MVVMILiveData.TypeData(
            MainData(
                "virtual.solid.snake@gmail.com",
                "vssnake"
            )
        )
    }

    private fun processError(typeErrorString : String){
        liveData.value = MVVMILiveData.Error(Throwable(typeErrorString))
    }

    private fun sendCommand(){
        liveData.value = MVVMILiveData.Event2View(MainModelEvent.StartStopLocation(true))
    }

    override fun onViewInitialized() {
    }


}






