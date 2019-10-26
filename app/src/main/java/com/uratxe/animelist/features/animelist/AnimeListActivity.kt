package com.uratxe.animelist.features.animelist

import android.os.Bundle
import com.uratxe.movetilt.*
import com.uratxe.mvit.BaseActivity
import com.uratxe.mvit.BaseModels
import kotlin.reflect.KClass

class AnimeListActivity : BaseActivity<AnimeListViewModel, Extended>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)
    }

    override fun layoutId(): Int { return R.layout.activity_anime_list }

    override fun getViewModelClass(): KClass<AnimeListViewModel> { return AnimeListViewModel::class }

    override fun setupViews() {

    }


}


sealed class AnimeListViewEvent  {
    object RetrieveUserEvent : AnimeListViewEvent()
    data class ProcessErrorEvent(val typeError : String) : AnimeListViewEvent()
}



sealed class DeliveryStatus {
    class Delivered : DeliveryStatus()
    class Delivering : DeliveryStatus()
    class NotDelivered(val error: String) : DeliveryStatus()
}


class Extended(override val viewData: View, override val modelData: Model) : BaseModels<String, Extended.Direction, Extended.ContentType>(){

    sealed class Direction  {
        class Incoming(val from: String) : Direction()
        class Outgoing(val status: DeliveryStatus) : Direction()
    }

    sealed class ContentType {
        class Text(val body: String) : ContentType()
        class Image(val url: String, val caption: String) : ContentType()
        class Audio(val url: String, val duration: Int) : ContentType()
    }

}