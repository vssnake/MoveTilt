package com.uratxe.movetilt.main

data class MainData(val email: String,val nickName : String)


sealed class MainViewEvent  {
    object RetrieveUserEvent : MainViewEvent()
    data class ProcessErrorEvent(val typeError : String) : MainViewEvent()
}

sealed class MainModelEvent{
    object LaunchActivity : MainModelEvent()
    data class StartStopLocation(val start : Boolean) : MainModelEvent()
}

