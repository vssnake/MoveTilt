package com.unatxe.mvvmi.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.core.app.TaskStackBuilder
import java.util.*

fun <T> List<T>.getSafeValue(index : Int) : T? {
    return try {
        get(index)
    }catch (ignore : Exception){
        null
    }
}

inline fun <T> T?.ifNotNull(block: (T) -> Unit): T? {
    return this?.also {
        block(it)
    }
}

inline fun <T> T?.ifNull(block: () -> Unit) : T? {
    if (this.isNull()) block()
    return this
}

fun <T> T?.isNull() : Boolean{
    return this == null
}
fun <T> T?.isNotNull() : Boolean{
    return this != null
}


fun Boolean.toInt() = if (this) 1 else 0


@ExperimentalStdlibApi
fun String.toCapitalize() : String {
    return toLowerCase(Locale.ROOT).capitalize(Locale.ROOT)
}



