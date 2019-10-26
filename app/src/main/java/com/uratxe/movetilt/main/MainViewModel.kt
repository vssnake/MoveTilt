package com.uratxe.movetilt.main

import android.app.Application
import com.uratxe.mvit.BaseLiveData
import com.uratxe.mvit.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel<MainData, MainViewEvent, MainModelEvent>(application) {

    override fun onEventFromView(commands: MainViewEvent) {

        when (commands){
            is MainViewEvent.RetrieveUserEvent -> retrieveUser()
            is MainViewEvent.ProcessErrorEvent -> {processError(commands.typeError)}
        }
    }

    private fun retrieveUser(){
        liveData.value = BaseLiveData.TypeData(
            MainData(
                "virtual.solid.snake@gmail.com",
                "vssnake"
            )
        )
    }

    private fun processError(typeErrorString : String){
        liveData.value = BaseLiveData.Error(Throwable(typeErrorString))
    }

    private fun sendCommand(){
        liveData.value = BaseLiveData.Event2View(MainModelEvent.StartStopLocation(true))
    }


}






