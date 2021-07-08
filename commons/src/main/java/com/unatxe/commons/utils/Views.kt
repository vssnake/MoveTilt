package com.unatxe.commons.utils

import android.graphics.Typeface
import android.text.InputType
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.unatxe.commons.validators.IDCardValidator

/**
 * Created by vssnake on 05/01/2018.
 */
fun Toolbar.changeToolbarFont(typeface: Typeface){

    for (i in 0..childCount){
        val view = getChildAt(i)
        if (view is TextView){
            view.typeface = typeface
            break
        }
    }

}
fun EditText.checkFlag(flags: Int, flag: Int): Boolean{
    return flags and flag == flag
}




fun EditText.isValid() : Boolean {
    if (checkFlag(inputType, InputType.TYPE_CLASS_PHONE)) {
        return isValidPhone()
    }
    if (checkFlag(inputType, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)) {
        return isValidEmail()
    }
    throw UnsupportedOperationException()
}

fun EditText.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

fun EditText.isValidPhone(): Boolean {
    return Patterns.PHONE.matcher(text).matches()
}

fun EditText.isValidNIF(countries: IDCardValidator.COUNTRIES) : Boolean{
    return IDCardValidator.instance.validateIDCARD(countries,editableText.toString())
}

enum class FieldType { NUMBER, DNI,CIF, EMAIL, NORMAL }

fun EditText.required() {
    noRequired()
    hint = "$hint *"
}

fun EditText.noRequired() {
    if(isRequired())
        hint = hint?.dropLast(2)
}

fun EditText.isRequired() : Boolean {
    return hint.indexOf(" *") != -1
}

fun EditText.isValid(fieldType: FieldType,
                     countries: IDCardValidator.COUNTRIES = IDCardValidator.COUNTRIES.ES,
                     response : (Pair<Boolean, FormErrorType?>)->(Unit)
                     ) {
    var isValid = true
    var formErrorType: FormErrorType? = null

    if(isRequired() && editableText.isBlank()) {
        isValid = false
        formErrorType = FormErrorType.EMPTY
    }
    else {
        when(fieldType) {
            FieldType.NUMBER -> {
                if (isValidPhone()) isValid = false
                formErrorType = FormErrorType.FORMAT
            }
            FieldType.CIF ->{
                if (isValidNIF(countries)) isValid = false
                formErrorType = FormErrorType.FORMAT
            }
            FieldType.DNI -> {
                if (isValidNIF(countries)) isValid = false
                formErrorType = FormErrorType.FORMAT
            }
            FieldType.EMAIL -> {
                if (isValidEmail()) isValid = false
                formErrorType = FormErrorType.EMAIL
            }
        }
    }

    response.invoke(Pair(isValid,formErrorType))
}

enum class FormErrorType(){
    EMPTY, FORMAT, EMAIL
}
fun EditText.onlyNumbers(text: String) : Boolean {
    var i = 0
    var j: Int
    var number = "" // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
    var myDNI = "" // Guardamos en una cadena los números para después calcular la letra
    val oneToNine = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    while (i < text.length - 1) {
        number = text.substring(i, i + 1)

        j = 0
        while (j < oneToNine.size) {
            if (number == oneToNine[j]) {
                myDNI += oneToNine[j]
            }
            j++
        }
        i++
    }

    return !(myDNI.length != 8)
}

private fun getDNILetter(dni: String): String {
    val myDNI = Integer.parseInt(dni.substring(0, 8))
    var mod = 0
    var myLetter = ""
    val letterAssignment = arrayOf("T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E")
    mod = myDNI % 23
    myLetter = letterAssignment[mod]

    return myLetter
}
