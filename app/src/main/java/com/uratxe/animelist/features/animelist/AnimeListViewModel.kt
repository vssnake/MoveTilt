package com.uratxe.animelist.features.animelist

import android.app.Application
import androidx.lifecycle.asLiveData
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.uratxe.movetilt.collectMain
import com.uratxe.movetilt.ioPool
import com.uratxe.mvit.MVVMIViewModel
import com.uratxe.mvit.Either
import com.uratxe.mvit.MVVMILiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class AnimeListViewModel(application: Application, val animeRepository: AnimeRepository)
    : MVVMIViewModel<AnimeListQuery.Data,AnimeListViewEvent,AnimeListModelEvent>(application) {



    var numberPage : Int = 1


    @UseExperimental(InternalCoroutinesApi::class)
    override fun onEventFromView(commands: AnimeListViewEvent) {

        when (commands){
            AnimeListViewEvent.OnMorePagesLoad -> getMoreAnimes()
        }
    }

    @InternalCoroutinesApi
    override fun onViewInitialized() {

        liveData.value = MVVMILiveData.Loading(true)

        getMoreAnimes()


    }

    @InternalCoroutinesApi
    fun getMoreAnimes(){
        animeRepository.getAnimes(numberPage).collectMain{

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










