package com.uratxe.animelist.features.animelist

import com.unatxe.mvvmi.ModelFromViewInterface

data class AnimeListData(val email: String,val nickName : String)

object OnMorePagesLoad : com.unatxe.mvvmi.ModelFromViewInterface

sealed class AnimeListViewEvent  {
    data class ProcessErrorEvent(val typeError : String) : AnimeListViewEvent()
}

sealed class AnimeListModelEvent{
    object LaunchActivity : AnimeListModelEvent()
    data class StartStopLocation(val start : Boolean) : AnimeListModelEvent()
}

