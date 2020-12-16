package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.unatxe.mvvmi.MVVMILiveData
import com.unatxe.mvvmi.ModelFromViewInterface
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.unatxe.mvvmi.MVVMIViewModel
import kotlinx.coroutines.flow.collect


class AnimeListViewModel(application: Application, handle: SavedStateHandle,private val animeRepository: AnimeRepository)
    : com.unatxe.mvvmi.MVVMIViewModel<AnimeListQuery.Data>(application) {



    var numberPage : Int = 1


   /* @UseExperimental(InternalCoroutinesApi::class)
    override fun onEventFromView(commands: AnimeListViewEvent) {

        when (commands){
            AnimeListViewEvent.OnMorePagesLoad -> getMoreAnimes()
        }
    }*/

    override fun onEventFromView(commands: com.unatxe.mvvmi.ModelFromViewInterface) {
        if (commands is OnMorePagesLoad){
            getMoreAnimes()
        }
    }


    override fun onViewInitialized() {

        liveData.value = com.unatxe.mvvmi.MVVMILiveData.Loading(true)

        getMoreAnimes()
    }


    private fun getMoreAnimes(){

        launch {
            animeRepository.getAnimes(numberPage).collect{

                liveData.value = com.unatxe.mvvmi.MVVMILiveData.Loading(false)

                it.either(
                    { failure ->
                      
                        liveData.value = com.unatxe.mvvmi.MVVMILiveData.Error(failure)
                    },
                    {data ->
                        numberPage = numberPage.inc()
                        liveData.value = com.unatxe.mvvmi.MVVMILiveData.TypeData(data)
                    })
            }
        }
    }


}










