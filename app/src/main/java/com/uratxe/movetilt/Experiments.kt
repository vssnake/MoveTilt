package com.uratxe.movetilt

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uratxe.mvit.MVVMIDelegate
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


val ioPool = newFixedThreadPoolContext(3,"IO")
val mainPool = CoroutineScope(Dispatchers.Main)

@InternalCoroutinesApi
inline fun <T>Flow<T>.collectMain(crossinline action: suspend (value: T) -> Unit): Job =

    GlobalScope.launch(Dispatchers.Main) {
        collect(object : FlowCollector<T> {
            override suspend fun emit(value: T) = action(value)
        })
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