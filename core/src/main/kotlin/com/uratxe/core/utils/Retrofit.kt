package com.baturamobile.utils

import android.util.Log
import com.baturamobile.utils.data.exception.exception.RestException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Buffer
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

fun Response<*>.getHeaderdate() : DateTime {
    return try {
        DateTimeFormat
            .forPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
            .withLocale(Locale.ENGLISH)
            .parseDateTime(headers().get("Date"))
    }catch (exception : Throwable){
        DateTime.now()
    }
}

fun Request.generateLog() : String{
    val result = StringBuilder()
    return result.append("Headers : ")
        .append(headers)
        .append("\n")
        .append("Method: ")
        .append(method)
        .append(" ")
        .append( url )
        .append("\n")
        .append("Body : ")
        .append(bodyToString()).toString()
}

fun Request.bodyToString(): String? {
    return try {
        val copy = newBuilder().build()
        val buffer = Buffer()
        copy.body?.writeTo(buffer)
        buffer.readUtf8()
    } catch (e: IOException) {
        "did not work"
    }
}

fun <T> Call<T>.queueExtension(callbackCustom: CallbackCustom<T>){
    GlobalScope.launch {
        try {
            val response = execute()
            GlobalScope.launch(Dispatchers.Main){
                callbackCustom.onResponse(this@queueExtension,response)
            }
        }catch (e: Throwable){
            GlobalScope.launch(Dispatchers.Main){
                callbackCustom.onFailure(this@queueExtension,e)
            }

        }

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
    fun onFailure(call: Call<T>, t: Throwable)
}

fun <ErrorModel>okhttp3.Response.responseChecker(typeErrorModel : Type) : RestException<ErrorModel>? where ErrorModel : Any  {
    var exception : RestException<ErrorModel>? = null
    if (!isSuccessful){
        val gson = Gson()
        var detailMesage: String = ""


        val stringBody = body?.string()
        val errorDTO = gson.fromJson<ErrorModel>(stringBody, typeErrorModel)


        LogStaticV2.logInterfaceV2?.log(
            Log.DEBUG, "RetrofitEndPointChecker2",
            request.url.toString() + " | " + code.toString() + " / " + detailMesage
        )

        exception = RestException(code, errorDTO)
    }

    return exception
}

fun <T> Call<T>.newCallback(success: (response: Response<T>?) -> Unit, failure: ((exceptionError: Throwable?)
-> Unit?)? = null) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable?) {
            failure?.invoke(t)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            success(response)
        }
    })
}
