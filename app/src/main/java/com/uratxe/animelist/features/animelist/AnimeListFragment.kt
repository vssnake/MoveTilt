package com.uratxe.animelist.features.animelist

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.uratxe.AnimeListQuery
import com.uratxe.movetilt.*
import kotlinx.android.synthetic.main.activity_anime_list.*

import kotlin.reflect.KClass

class AnimeListFragment : KoinProyectFragment<AnimeListViewModel, AnimeListQuery.Data, AnimeListViewEvent, AnimeListModelEvent>() {


    val adapter = AnimeListAdapter()

    override fun layoutId(): Int { return R.layout.activity_anime_list }

    override fun getViewModelClass(): KClass<AnimeListViewModel> { return AnimeListViewModel::class }

    override fun setupViews() {

        aal_rv.adapter = adapter
        aal_rv.layoutManager = LinearLayoutManager(context)


    }

    override fun onModelReceived(data: AnimeListQuery.Data) {
        adapter.loadData(data)
    }

    override fun onEventModelReceived(data: AnimeListModelEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}