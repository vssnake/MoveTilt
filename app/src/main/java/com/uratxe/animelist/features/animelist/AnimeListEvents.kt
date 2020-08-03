package com.uratxe.animelist.features.animelist

import com.uratxe.mvit.ModelFromViewInterface

data class AnimeListData(val email: String,val nickName : String)

object OnMorePagesLoad : ModelFromViewInterface

sealed class AnimeListViewEvent  {
    data class ProcessErrorEvent(val typeError : String) : AnimeListViewEvent()
}

sealed class AnimeListModelEvent{
    object LaunchActivity : AnimeListModelEvent()
    data class StartStopLocation(val start : Boolean) : AnimeListModelEvent()
}

