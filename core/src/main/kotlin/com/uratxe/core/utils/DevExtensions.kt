package com.uratxe.core.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.core.app.TaskStackBuilder
import retrofit2.Call
import retrofit2.Response
import java.util.*


object PendingIntentHelper{
    fun addBackStack(context : Context,requestCode : Int, intent : Intent,flags : Int): PendingIntent? {
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context.applicationContext)
        stackBuilder.addNextIntentWithParentStack(intent)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}

fun <T> newCallbackV2(success: (T?) -> Unit,
                      failure: ((codeError: Int?, stringError: String?, exceptionError: Throwable?)
                      -> Unit?)? = null):
        CallbackV2<T> {
    return object : CallbackV2<T?>() {

        override fun onResponse(dataResponse: T?) {
            success(dataResponse)
        }

        override fun onError(codeError: Int?, stringError: String?, exceptionError: Throwable?) {
            failure?.invoke(codeError, stringError, exceptionError)
            super.onError(codeError, stringError, exceptionError)
        }

    }
}

open class CallbackV2<in T> {

    open fun onResponse(dataResponse: T) {}

    @CallSuper
    open fun onError(codeError: Int?, stringError: String?, exceptionError: Throwable?) {
        LogStaticV2.logInterfaceV2?.crashError(stringError,exceptionError)
    }
}

interface CallbackCustom<T> {
    /**
     * Invoked for a received HTTP response.
     *
     *
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call [Response.isSuccessful] to determine if the response indicates success.
     */
    fun onResponse(call: Call<T>, response: Response<T>)

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    fun onFailure(call: Call<T>, stringError: String?, t: Throwable)
}





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



