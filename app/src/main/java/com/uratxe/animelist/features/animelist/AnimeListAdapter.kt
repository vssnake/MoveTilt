package com.uratxe.animelist.features.animelist

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uratxe.AnimeListQuery
import com.uratxe.movetilt.PaginationScrollListener
import com.uratxe.movetilt.R
import kotlinx.android.synthetic.main.viewholder_anime_list.view.*

class AnimeListAdapter(val animeListViewModel: AnimeListViewModel)
    : ListAdapter<AnimeListQuery.Medium,AnimeListViewHolder>(DefaultItemCallback<AnimeListQuery.Medium>(AnimeListQuery.Medium::id)) {

    var isLoading = false
    var isLastPage = false


    internal fun loadData (data : AnimeListQuery.Data){
        isLoading = false
        isLastPage = !data.page!!.pageInfo!!.hasNextPage!!
        submitList(data.page.media!!)
    }



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                animeListViewModel.lauchEventFromView(OnMorePagesLoad)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {

        return AnimeListViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.viewholder_anime_list,parent,false))
    }

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DefaultItemCallback<T>(private val idSelector: ((T) -> Any?)? = null) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return idSelector?.let { return it(oldItem) == it(newItem) } ?: oldItem == newItem
    }
    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}


class AnimeListViewHolder(view : View) : RecyclerView.ViewHolder(view){
    fun bind(medium: AnimeListQuery.Medium) {

        itemView.vhal_title.text = medium.title?.romaji
        itemView.vhal_description.text = Html.fromHtml(medium.description)
        Glide.with(itemView.context).load(medium.coverImage?.large).into(itemView.vhal_image)

    }

}