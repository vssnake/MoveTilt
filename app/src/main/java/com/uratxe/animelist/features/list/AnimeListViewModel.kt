package com.uratxe.animelist.features.list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.unatxe.mvvmi.*
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.features.list.data.AnimeRepository
import com.uratxe.common.launch
import kotlinx.coroutines.flow.collect

class AnimeListViewModel(application: Application, private val animeRepository: AnimeRepository)
    : MVVMIViewModel<AnimeListViewData>(application) {

    var numberPage : Int = 1

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        if (commands is OnMorePagesLoad){
            getMoreAnimes()
        }
    }

    override fun initViewData(): AnimeListViewData {
        return AnimeListViewData()
    }

    override fun onViewInitialized() {
        viewData.loading.value = ShowLoading(true)
        getMoreAnimes()
    }

    private fun getMoreAnimes(){

        launch {
            animeRepository.getAnimes(numberPage).collect {

                viewData.loading.value = ShowLoading(false)

                it.either(
                    { failure ->
                        viewData.error.value = failure
                    },
                    {data ->
                        numberPage = numberPage.inc()
                        viewData.animeListView.value = data
                    }
                )
            }
        }
    }

}

data class AnimeListViewData(
   val animeListView: MutableLiveData<AnimeListQuery.Data> = MutableLiveData()
): MVVMIData()








