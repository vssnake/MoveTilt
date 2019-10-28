package com.uratxe.animelist.features.animelist

import android.app.Application
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.mvit.BaseViewModel

class AnimeListViewModel(application: Application) : BaseViewModel<AnimeListData,AnimeListViewEvent,AnimeListModelEvent>(application) {



    fun test(){

    }

    override fun onEventFromView(commands: AnimeListViewEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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









