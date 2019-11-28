package com.uratxe.movetilt

import android.widget.Toast
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