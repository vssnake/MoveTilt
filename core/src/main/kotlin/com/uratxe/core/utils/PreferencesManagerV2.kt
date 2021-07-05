package com.uratxe.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type

object PreferencesManagerV2 {
    private const val PRIVATE_PREFS_NAME = "BackusPrefs"
    private var prefs: SharedPreferences? = null

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(
                PRIVATE_PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getBoolean(key: String, def: Boolean): Boolean {
        return prefs?.getBoolean(key, def) ?: def
    }

    fun setBoolean(key: String, value: Boolean) {
        prefs?.let {
            val editor = it.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun getInt(key: String, def: Int): Int {
        return prefs?.getInt(key, def) ?: def
    }

    fun setInt(key: String, value: Int) {
        prefs?.let{
            val editor = it.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    fun getString(key: String, def: String?): String? {
        return prefs?.getString(key, def) ?: def
    }

    fun setString(key: String, value: String) {
        prefs?.let{
            val editor = it.edit()
            editor.putString(key, value).apply()
        }
    }

    fun removeString(key: String){
        prefs?.let{
            val editor = it.edit()
            editor.remove(key).commit()
        }
    }

    fun setObject(key: String,test : Any, type : Type){
        val gson = Gson()

        val data = gson.toJson(test,type)

        setString(key, data)
    }

    fun <DesObject>getObject(key : String, type : Type) : DesObject?{
        val gson = Gson()

        val stringToDeserialize =
            getString(key, null)

        return if (stringToDeserialize == null){
            null
        }else{
            try {
                gson.fromJson<DesObject>(stringToDeserialize,type)
            }catch (_ : Throwable){
                null
            }

        }
    }


}