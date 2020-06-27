package com.uratxe.animelist.features.animelist

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.uratxe.AnimeListQuery
import com.uratxe.movetilt.*
import kotlinx.android.synthetic.main.activity_anime_list.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.coroutines.launch

import kotlin.reflect.KClass

class AnimeListFragment : KoinProyectFragment<AnimeListViewModel, AnimeListQuery.Data, AnimeListViewEvent, AnimeListModelEvent>() {

    private val adapter by lazy {AnimeListAdapter(viewModel)}

    override fun layoutId(): Int { return R.layout.activity_anime_list }

    override fun getViewModelClass(): KClass<AnimeListViewModel> { return AnimeListViewModel::class }

    override fun setupViews() {

        aal_rv.layoutManager = LinearLayoutManager(context)
        aal_rv.adapter = adapter


        motionLayout.loop(true)

    }

    override fun onModelReceived(data: AnimeListQuery.Data) {
        adapter.loadData(data)

    }

    override fun onEventModelReceived(data: AnimeListModelEvent) {

    }


}