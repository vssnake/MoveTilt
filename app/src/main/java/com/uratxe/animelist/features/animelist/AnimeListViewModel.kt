package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.asLiveData
import com.uratxe.mvit.MVVMIViewModel
import com.uratxe.mvit.Either
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class AnimeListViewModel(application: Application) : MVVMIViewModel<AnimeListData,AnimeListViewEvent,AnimeListModelEvent>(application) {


    val animeRepository = AnimeRepository()


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

    @InternalCoroutinesApi
    override fun onViewInitialized() {
        GlobalScope.launch {
            animeRepository.animes()
        }

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










