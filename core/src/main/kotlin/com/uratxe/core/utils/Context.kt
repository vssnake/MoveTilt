package com.baturamobile.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showVersionName() : String{
    return try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName
        version
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

fun Context.getDefaultNfcAdapter(): NfcAdapter? {
    val manager = getSystemService(Context.NFC_SERVICE) as NfcManager
    return manager.defaultAdapter
}

fun Context.startNfcSettingsActivity() {
    if (android.os.Build.VERSION.SDK_INT >= 16) {
        startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
    } else {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}

fun Context.isWifiConnected(): Boolean{
    val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    return wifi.isConnected
}

fun Context.isInternetAvaiable() : Boolean{
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

fun Context.hideKeyboard(view: View?) {
    view?.let {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }

}
