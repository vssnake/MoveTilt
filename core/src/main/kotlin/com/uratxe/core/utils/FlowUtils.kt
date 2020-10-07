package com.uratxe.core.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn

fun <T>getNormalFlow(block: suspend FlowCollector<T>.() -> Unit) : Flow<T> {
    return flow<T>{
        block.invoke(this)
    }.launchUI()
}

fun <T> Flow<T>.launchUI() : Flow<T> {
    launchIn(MainScope())
    return this
}

fun <T> Flow<T>.launchGlobal() : Flow<T> {
    launchIn(GlobalScope)
    return this
}