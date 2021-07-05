package com.uratxe.core.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Manejador de las SharedPreferences.
 *
 * Created by jrecio.
 */
object PreferencesManager {
    /**
     * Nombre del archivo de las preferences.
     */
    const val PRIVATE_PREFS_NAME = "BackusPrefs"
    /**
     * Objeto SharedPreferences.
     */
    internal var prefs: SharedPreferences? = null

    /***
     * Inicializar las preferences.
     */
    fun init(context: Context) {
        if (null == prefs) {
            prefs = context.getSharedPreferences(
                PRIVATE_PREFS_NAME,
                Context.MODE_PRIVATE
            )
        }
    }

    /**
     * Devuelve un valor boolean.
     *
     * @param key - Nombre de la preference.
     * @param def - Valor por defecto.
     * @return Valor de la preference en boolean.
     */
    fun getBoolean(key: String?, def: Boolean): Boolean {
        return if (null != prefs) prefs!!.getBoolean(
            key,
            def
        ) else def
    }

    /**
     * Establece un valor boolean.
     *
     * @param key - Nombre de la preference.
     * @param value - Valor de la preference.
     */
    fun setBoolean(key: String?, value: Boolean) {
        if (null != prefs) {
            val editor = prefs!!.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    /**
     * Devuelve un valor int.
     *
     * @param key - Nombre de la preference.
     * @param def - Valor por defecto.
     * @return Valor de la preference en int.
     */
    fun getInt(key: String?, def: Int): Int {
        return if (null != prefs) prefs!!.getInt(
            key,
            def
        ) else def
    }

    /**
     * Establece un valor int.
     *
     * @param key - Nombre de la preference.
     * @param value - Valor de la preference.
     */
    fun setInt(key: String?, value: Int) {
        if (null != prefs) {
            val editor = prefs!!.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    /**
     * Devuelve un valor String.
     *
     * @param key - Nombre de la preference.
     * @param def - Valor por defecto.
     * @return Valor de la preference en String.
     */
    fun getString(key: String?, def: String?): String? {
        return if (null != prefs) prefs!!.getString(
            key,
            def
        ) else def
    }

    /**
     * Establece un valor String.
     *
     * @param key - Nombre de la preference.
     * @param value - Valor de la preference.
     */
    fun setString(key: String?, value: String?) {
        if (null != prefs) {
            val editor = prefs!!.edit()
            editor.putString(key, value).apply()
        }
    }
}