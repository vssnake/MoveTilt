package com.uratxe.movetilt

import android.app.Application

class MainViewModel(application: Application) : BaseViewModel<MainData,MainActivityCommands>(application) {



    override fun onCommandSend(commands: MainActivityCommands) {
        when (commands){
            MainActivityCommands.RetrieveUserCommand -> retrieveUser()
            MainActivityCommands.ProcessErrorCommand -> processError()

        }
    }


    private fun retrieveUser(){
        liveData.value = BaseLiveData.TypeData(MainData("virtual.solid.snake@gmail.com","vssnake"))
    }

    private fun processError(){
        liveData.value = BaseLiveData.Error(Throwable("Prueba"))
    }
}

data class MainData(val email: String,val nickName : String)




