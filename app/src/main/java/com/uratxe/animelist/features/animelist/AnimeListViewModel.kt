package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.unatxe.mvvmi.MVVMILiveData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.uratxe.movetilt.launch
import kotlinx.coroutines.flow.collect


class AnimeListViewModel(application: Application, handle: SavedStateHandle,private val animeRepository: AnimeRepository)
    : MVVMIViewModel<AnimeListQuery.Data>(application) {

    var numberPage : Int = 1

   /* @UseExperimental(InternalCoroutinesApi::class)
    override fun onEventFromView(commands: AnimeListViewEvent) {

        when (commands){
            AnimeListViewEvent.OnMorePagesLoad -> getMoreAnimes()
        }
    }*/

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        if (commands is OnMorePagesLoad){
            getMoreAnimes()
        }
    }


    override fun onViewInitialized() {
        liveData.value = MVVMILiveData.Loading(true)
        getMoreAnimes()
    }


    private fun getMoreAnimes(){

        launch {
            animeRepository.getAnimes(numberPage).collect {

                liveData.value = MVVMILiveData.Loading(false)

                it.either(
                    { failure ->
                        liveData.value = MVVMILiveData.Error(failure)
                    },
                    {data ->
                        numberPage = numberPage.inc()
                        liveData.value = MVVMILiveData.TypeData(data)
                    }
                )
            }
        }
    }


}










