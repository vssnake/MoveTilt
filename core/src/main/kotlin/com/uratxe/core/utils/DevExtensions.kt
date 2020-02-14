package com.baturamobile.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.CallSuper
import androidx.core.app.TaskStackBuilder


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


