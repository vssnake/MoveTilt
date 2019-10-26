package com.uratxe.animelist.features.animelist

import android.app.Application
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.mvit.BaseViewModel

class AnimeListViewModel(application: Application) : BaseViewModel<Extended>(application) {



    fun test(){

    }

    /*override fun onEvent(commands: AnimeListViewEvent) {

        when (commands){
            is AnimeListViewEvent.RetrieveUserEvent -> retrieveUser()
            is AnimeListViewEvent.ProcessErrorEvent -> {processError(commands.typeError)}

        }
    }


    private fun retrieveUser(){
        liveData.value = BaseLiveData.TypeData(AnimeListData("virtual.solid.snake@gmail.com","vssnake"))
    }

    private fun processError(typeErrorString : String){
        liveData.value = BaseLiveData.Error(Throwable(typeErrorString))
    }*/
}

data class AnimeListData(val email: String,val nickName : String)







