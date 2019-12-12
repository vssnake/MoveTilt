package com.uratxe.animelist.features.animelist

data class AnimeListData(val email: String,val nickName : String)


sealed class AnimeListViewEvent  {
    object OnMorePagesLoad : AnimeListViewEvent()
    data class ProcessErrorEvent(val typeError : String) : AnimeListViewEvent()
}

sealed class AnimeListModelEvent{
    object LaunchActivity : AnimeListModelEvent()
    data class StartStopLocation(val start : Boolean) : AnimeListModelEvent()
}

