package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.mvit.BaseViewModel
import com.uratxe.mvit.Either
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class AnimeListViewModel(application: Application) : BaseViewModel<AnimeListData,AnimeListViewEvent,AnimeListModelEvent>(application) {




    @ExperimentalCoroutinesApi
    fun test(){
        val test = flow<Either<Throwable,String>> {
            emit(Either.Right("Hello"))
        }.catch { e->
            emit(Either.Left(e))
        }

        val referred = CompletableDeferred("")






        test.asLiveData(MainScope().coroutineContext)







    }

    val cathMethod : suspend FlowCollector<Either<Throwable,Any>>.(Throwable) -> Unit = { e->
        emit(Either.Left(e))
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










