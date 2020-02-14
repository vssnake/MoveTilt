package com.baturamobile.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun Context.getColorFromTheme(styleTheme : Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(styleTheme,typedValue,true)
    return ContextCompat.getColor(this,typedValue.resourceId)

}

fun Context.getColorIdFromTheme(styleTheme : Int) : Int{
    val typedValue = TypedValue()
    val theme = theme
    theme.resolveAttribute(styleTheme,typedValue,true)
    return typedValue.resourceId
}

fun Activity.changeStatusBarColor( @ColorRes color : Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)

    }
}



