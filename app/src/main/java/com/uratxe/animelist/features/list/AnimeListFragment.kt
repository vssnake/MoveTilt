package com.uratxe.animelist.features.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.uratxe.common.KoinProyectFragment
import com.uratxe.movetilt.R
import kotlinx.android.synthetic.main.activity_anime_list.*
import kotlin.reflect.KClass

class AnimeListFragment : KoinProyectFragment<AnimeListViewModel, AnimeListViewData>() {

    private val adapter by lazy {AnimeListAdapter(viewModel)}

    override fun layoutId(): Int { return R.layout.activity_anime_list }

    override fun getViewModelClass(): KClass<AnimeListViewModel> { return AnimeListViewModel::class }

    override fun setupViews() {
        aal_rv.layoutManager = LinearLayoutManager(context)
        aal_rv.adapter = adapter
    }

    override fun onModelInitialized(data: AnimeListViewData) {
        data.animeListView.observe(this) { animeList ->
            adapter.loadData(animeList)
        }
    }
}