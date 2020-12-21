package com.baturamobile.utils

import android.widget.TextView
import java.util.*

fun String.getAccessibilityText(): String {
    val result = this.toLowerCase(Locale.getDefault()).trim()
        .replace("-", "/")
        .split('/')
    return result.joinToString(" / ") {
        it.capitalize()
    }
}


fun TextView.setTextWithContentDescription(string : String){

    this.text = string
    this.contentDescription = string.getAccessibilityText()
}
