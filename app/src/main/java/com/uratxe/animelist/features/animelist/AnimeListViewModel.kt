package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.uratxe.movetilt.launch
import com.uratxe.mvit.MVVMILiveData
import com.uratxe.mvit.ModelFromViewInterface
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.uratxe.mvit.MVVMIViewModel
import kotlinx.coroutines.flow.collect


class AnimeListViewModel(application: Application,handle: SavedStateHandle, val animeRepository: AnimeRepository)
    : MVVMIViewModel<AnimeListQuery.Data>(application) {



    var numberPage : Int = 1


   /* @UseExperimental(InternalCoroutinesApi::class)
    override fun onEventFromView(commands: AnimeListViewEvent) {

        when (commands){
            AnimeListViewEvent.OnMorePagesLoad -> getMoreAnimes()
        }
    }*/

    override fun onEventFromView(commands: ModelFromViewInterface) {
        TODO("Not yet implemented")
    }


    override fun onViewInitialized() {

        liveData.value = MVVMILiveData.Loading(true)

        getMoreAnimes()


    }


    fun getMoreAnimes(){

        launch {
            animeRepository.getAnimes(numberPage).collect{

                liveData.value = MVVMILiveData.Loading(false)

                it.either(
                    { failure ->
                      
                        liveData.value = MVVMILiveData.Error(failure)
                    },
                    {data ->
                        numberPage = numberPage.inc()
                        liveData.value = MVVMILiveData.TypeData(data)
                    })
            }
        }
    }


}










