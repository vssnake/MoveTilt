package com.uratxe.core.validators

/**
 * Created by vssnake on 27/10/2017.
 */

import com.uratxe.core.validators.IDCardValidator.COUNTRIES.valueOf

class IDCardValidator private constructor() {

    private object Holder {
        val INSTANCE = IDCardValidator()
    }

    companion object {
        val instance: IDCardValidator by lazy { Holder.INSTANCE }
    }

    enum class COUNTRIES {
        ES,
    }

    private val countries = HashMap<COUNTRIES, Regex>()
    private val spanishLetterVerified: HashMap<Int, String>

    init {

        countries[COUNTRIES.ES] = Regex("^[X-Z]?[0-9]{8}[A-Za-z]\$")

        spanishLetterVerified = hashMapOf(0 to "T", 1 to "R", 2 to "W", 3 to "A", 4 to "G",
                5 to "M", 6 to "Y", 7 to "F", 8 to "P", 9 to "D", 10 to "X", 11 to "B", 12 to "N", 13 to "J",
                14 to "Z", 15 to "S", 16 to "Q", 17 to "V", 18 to "H", 19 to "L", 20 to "C", 21 to "K", 22 to "E")
    }

    fun validateIDCARD(isoCountry: String, codeCard: String): Boolean {
        return when (valueOf(isoCountry)) {
            COUNTRIES.ES -> isSpanishCardValid(codeCard.toUpperCase())

        }
    }

    fun validateIDCARD(country : COUNTRIES, codeCard: String): Boolean {
        return when (country) {
            COUNTRIES.ES -> isSpanishCardValid(codeCard.toUpperCase())
        }
    }


    private fun isSpanishCardValid(codeCard: String): Boolean {
        try {
            var modifiedCodeCard = codeCard
            if (countries[COUNTRIES.ES]!!.matches(codeCard) || Regex("^[XYZ]{1}[0-9]{7}[A-Z]{1}\$").matches(codeCard)) {

                if (codeCard[0] == 'X') modifiedCodeCard = codeCard.replaceRange(IntRange(0, 0), "0")
                if (codeCard[0] == 'Y') modifiedCodeCard = codeCard.replaceRange(IntRange(0, 0), "1")
                if (codeCard[0] == 'Z') modifiedCodeCard = codeCard.replaceRange(IntRange(0, 0), "2")

                val formattedCodeCard = if (modifiedCodeCard.length == 10) modifiedCodeCard.substring(1, 10) else modifiedCodeCard.substring(0, 9)
                val onlyNumber = Integer.valueOf(formattedCodeCard.substring(0, 8))
                val remainder = onlyNumber % 23
                if (spanishLetterVerified.containsKey(remainder)
                        && spanishLetterVerified[remainder] == (formattedCodeCard[8].toString())) {
                    return true
                }
            }
            return false
        } catch (e: Exception) {
            return false
        }

    }


}