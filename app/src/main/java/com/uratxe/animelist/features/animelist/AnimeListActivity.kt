package com.uratxe.animelist.features.animelist

import android.os.Bundle
import com.uratxe.movetilt.*

import kotlin.reflect.KClass

class AnimeListActivity : KoinProyectActivity<AnimeListViewModel, AnimeListData, AnimeListViewEvent, AnimeListModelEvent>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_list)
    }

    override fun layoutId(): Int { return R.layout.activity_anime_list }

    override fun getViewModelClass(): KClass<AnimeListViewModel> { return AnimeListViewModel::class }

    override fun setupViews() {

    }

    override fun onModelReceived(data: AnimeListData) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEventModelReceived(data: AnimeListModelEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}