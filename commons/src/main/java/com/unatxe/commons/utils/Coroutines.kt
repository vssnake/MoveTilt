package com.unatxe.commons.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun <T>getNormalFlow(block: suspend FlowCollector<T>.() -> Unit) : Flow<T> {
    return flow<T> {
        block.invoke(this)
    }
        .flowOn(Dispatchers.IO)
        .launchUI()
}

fun <T> Flow<T>.launchUI() : Flow<T> {
    launchIn(MainScope())
    return this
}

fun <T> Flow<T>.launchGlobal() : Flow<T> {
    launchIn(GlobalScope)
    return this
}

fun <T> runBlockingIO(block: suspend CoroutineScope.() -> T): T{
    return runBlocking(Dispatchers.IO,block)
}

fun runUI(block: suspend CoroutineScope.() -> Unit){
    GlobalScope.launch(Dispatchers.Main,block = block)
}

fun runIO(block: suspend CoroutineScope.() -> Unit){
    GlobalScope.launch(Dispatchers.IO,block = block)
}