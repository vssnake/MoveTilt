package com.uratxe.animelist.features.animelist

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uratxe.AnimeListQuery
import com.uratxe.movetilt.R
import kotlinx.android.synthetic.main.viewholder_anime_list.view.*

class AnimeListAdapter : RecyclerView.Adapter<AnimeListViewHolder>() {

    private val list = mutableListOf<AnimeListQuery.Medium>()

    internal fun loadData (data : AnimeListQuery.Data){
        list.addAll(data.page!!.media!!.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {

        return AnimeListViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.viewholder_anime_list,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {


        holder.bind(list[position])

    }

}

class AnimeListViewHolder(view : View) : RecyclerView.ViewHolder(view){
    fun bind(medium: AnimeListQuery.Medium) {
        itemView.vhal_title.text = medium.title?.romaji
        itemView.vhal_description.text = Html.fromHtml(medium.description)
        Glide.with(itemView.context).load(medium.coverImage?.large).into(itemView.vhal_image)

    }

}