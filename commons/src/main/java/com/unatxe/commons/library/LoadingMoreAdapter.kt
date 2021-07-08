package com.unatxe.commons.library

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.util.*

/**
 * Created by vssnake on 08/03/2018.
 */

/**
 * Adaptador para {@link androidx.recyclerview.widget.RecyclerView RecyclerView}
 * Cuando el usuario llega al final de la lista, se muestra un spinner como último elemento y se
 * notifica a través del <i>callback</i>.
 * Después es necesario indicar que se pare este spinner para que desaparezca {@link setLoaded()}
 *
 * @param callback donde nos indicará cuándo se llega al final de la lista
 * @param recyclerView al que lo queremos vincular
 * @param loadingLayout como ViewHolder que mostrar
 *
 */
abstract class LoadingMoreAdapter<T : Any>(callback: OnLoadMoreHolderCallback<T>,
                                           recyclerView: androidx.recyclerview.widget.RecyclerView,
                                           @LayoutRes val loadingLayout : Int) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(){

    private var isLoading = true

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    var onHolderCallback: OnLoadMoreHolderCallback<T>? = null
    private var lastVisibleItem: Int = 0
    private var totalItemCount:Int = 0
    private val visibleThreshold = 5

    internal var listItems: MutableList<T> = ArrayList()

    init {
        onHolderCallback = callback

       // if (recyclerView.layoutManager is LinearLayoutManager) {

            val linearLayoutManager = recyclerView
                    .layoutManager as androidx.recyclerview.widget.LinearLayoutManager


            recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold && listItems.size > 0) {
                        onHolderCallback?.onLoadMore()
                        isLoading = true
                    }
                }
            })
       // }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listItems.size) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    abstract fun getViewHolder(context: Context, parent: ViewGroup) : androidx.recyclerview.widget.RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            getViewHolder(parent.context,parent)
        } else  {
            val view = LayoutInflater.from(parent.context).inflate(loadingLayout, parent, false)
            createViewHolder(view)
        }
    }

    abstract fun createViewHolder(view: View) : LoadingViewHolder


    override fun getItemCount(): Int {
        return  listItems.size
    }

    fun addItems(itemsToAdd: List<T>) {
        if (listItems.isNotEmpty()){
            val anteriorSize : Int = listItems.size - 1

            listItems.addAll(itemsToAdd.subList(listItems.size, itemsToAdd.size))
            setLoaded()
            notifyItemRangeChanged(anteriorSize, listItems.size)
        }else{
            setItems(itemsToAdd)
        }

    }

    fun setLoaded() {
        isLoading = false
    }

    fun clearItems() {
        listItems.clear()
        notifyDataSetChanged()
    }

    fun setItems(itemsToAdd: List<T>) {
        listItems.clear()
        listItems.addAll(itemsToAdd)
        setLoaded()
        notifyDataSetChanged()
    }


}

 abstract class LoadingViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
}



interface OnLoadMoreHolderCallback<in T : Any> {
    fun onHolderClick(items : T, vararg view : View?)
    fun onLoadMore()
}