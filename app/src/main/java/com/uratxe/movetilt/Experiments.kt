package com.uratxe.movetilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unatxe.mvvmi.MVVMIDelegate
import com.uratxe.core.data.exceptions.Failure
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


val ioPool = newFixedThreadPoolContext(3,"IO")
val mainPool = CoroutineScope(Dispatchers.Main)

inline fun <T>Flow<T>.collectBlock(crossinline action: suspend (value: T) -> Unit) =
    runBlocking {
        collect{
            action(it)
        }
    }

inline fun ViewModel.launch(crossinline action: suspend CoroutineScope.() -> Unit) = run {
    viewModelScope.launch {
        action()
    }
}

class DerivedViewDelegate(b: MVVMIDelegate) : MVVMIDelegate by b{
    override fun processError(error: Failure) {
        //Toast.makeText(context,error.message,Toast.LENGTH_LONG ).show()
    }

    override fun showLoading(boolean: Boolean) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


/**
 * Pagination class to add more items to the list when reach the last item.
 */
abstract class PaginationScrollListener(val recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val visibleItemCount = linearLayoutHelper.childCount
            val totalItemCount = linearLayoutHelper.itemCount
            val firstVisibleItemPosition = linearLayoutHelper.findFirstVisibleItemPosition()

            if (!isLoading() && !isLastPage()) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }//                    && totalItemCount >= ClothesFragment.itemsCount
            }
        }

    }

    val linearLayoutHelper by lazy { recyclerView.layoutManager as LinearLayoutManager}
    abstract fun loadMoreItems()
}