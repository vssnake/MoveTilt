package com.baturamobile.utils

import android.util.Log
import com.google.gson.Gson
import com.uratxe.core.data.exceptions.RestException
import com.uratxe.core.utils.LogStaticV2
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Buffer
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext.getInstance
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

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

fun getUnsafeOkHttpClient(): OkHttpClient {
    return try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> =
            trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }

        val trustManager =
            trustManagers[0] as X509TrustManager


        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustManager)
        builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
        builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
