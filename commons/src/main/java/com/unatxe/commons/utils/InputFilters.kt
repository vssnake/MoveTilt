package com.unatxe.commons.utils

import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.text.Spanned

class HexaDecialInputFilter : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
                        dend: Int): CharSequence {

        if (source == "") { // for backspace
            return source
        }
        if (source.toString().isEmpty()) {
            return ""
        }
        val sourceString = source.toString()
        val resultBuilder = StringBuilder()

        val regex = Regex("^[A-Fa-f0-9]\$")
        for (i in sourceString.indices) {
            if (regex.matches(sourceString[i].toString())) {
                resultBuilder.append(sourceString[i])
            }
        }

        return resultBuilder.toString()
    }
}

class AlphaNunericDash : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
                        dend: Int): CharSequence {

        if (source == "") { // for backspace
            return source
        }
        if (source.toString().isEmpty()) {
            return ""
        }
        val sourceString = source.toString()
        val resultBuilder = StringBuilder()

        val regex = Regex("^[A-Za-z0-9-]\$")
        for (i in sourceString.indices) {
            if (regex.matches(sourceString[i].toString())) {
                resultBuilder.append(sourceString[i])
            }
        }

        return resultBuilder.toString()
    }
}

class InputFilterTextAndNumber : InputFilter {
    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence {
        if (source is SpannableStringBuilder) {
            for (i in end - 1 downTo start) {
                val currentChar = source[i]
                if (!Character.isLetterOrDigit(currentChar) && !Character.isSpaceChar(currentChar)) {
                    source.delete(i, i + 1)
                }
            }
            return source
        } else {
            val filteredStringBuilder = StringBuilder()
            source?.let {
                for (i in start until end) {
                    val currentChar = it[i]
                    if (Character.isLetterOrDigit(currentChar) || Character.isSpaceChar(currentChar)) {
                        filteredStringBuilder.append(currentChar)
                    }
                }
            }

            return filteredStringBuilder.toString()
        }
    }
}