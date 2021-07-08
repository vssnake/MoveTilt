package com.unatxe.commons.validators

/**
 * Created by vssnake on 27/10/2017.
 */
class VATValidator private constructor() {

    private object Holder {
        val INSTANCE = VATValidator()
    }

    companion object {
        val instance: VATValidator by lazy { Holder.INSTANCE }
    }

    private val countries = HashMap<IDCardValidator.COUNTRIES, Regex>()
    private val spanishLetterVerified: HashMap<Int, String>

    init {
        countries[IDCardValidator.COUNTRIES.ES] = Regex("^([ABCDEFGHJKLMNPQRSUVW])(\\d{7})([0-9A-J])\$")

        spanishLetterVerified = hashMapOf(0 to "J", 1 to "A", 2 to "B", 3 to "C",
                4 to "D", 5 to "E", 6 to "F", 7 to "G", 8 to "H", 9 to "I")
    }

    fun validateVAT(isoCountry: String, codeCard: String): Boolean {
        return try {
            when (IDCardValidator.COUNTRIES.valueOf(isoCountry)) {
                IDCardValidator.COUNTRIES.ES -> isSpanishCardValid(codeCard.toUpperCase())
            }
        } catch (e: Exception) {
            //If country not listed return true
            return true
        }

    }


    private fun isSpanishCardValid(codeCard: String): Boolean {
        if (countries[IDCardValidator.COUNTRIES.ES]!!.matches(codeCard)) {

            try {
                val centralDigists = codeCard.substring(1, 8)
                val evenSum = Integer.valueOf(centralDigists.substring(1, 2)) +
                        Integer.valueOf(centralDigists.substring(3, 4)) +
                        Integer.valueOf(centralDigists.substring(5, 6))

                val odd1 = sumDigits(Integer.valueOf(centralDigists.substring(0, 1)) * 2)

                val odd2 = sumDigits(Integer.valueOf(centralDigists.substring(2, 3)) * 2)

                val odd3 = sumDigits(Integer.valueOf(centralDigists.substring(4, 5)) * 2)

                val odd4 = sumDigits(Integer.valueOf(centralDigists.substring(6, 7)) * 2)

                val oddSum = odd1 + odd2 + odd3 + odd4

                val partialSum = evenSum + oddSum

                val eDigit = Integer.valueOf(partialSum.toString()[1].toString())

                val letterFinal: Int
                letterFinal = if (eDigit == 0) {
                    0
                } else {
                    10 - eDigit
                }

                val lastDigit = codeCard.substring(8, 9)
                if (lastDigit.toIntOrNull() == null) {
                    return spanishLetterVerified.containsKey(letterFinal)
                            && spanishLetterVerified[letterFinal].equals(lastDigit, true)
                } else {
                    if (lastDigit.toInt() == letterFinal) {
                        return true
                    }
                }
                return false

            } catch (e: Exception) {
                return false
            }


        }
        return false
    }


    private fun sumDigits(number: Int): Int {
        return sumDigits(number.toString())
    }

    private fun sumDigits(number: String): Int {
        var sum = 0
        for (digit in number) {
            sum += Integer.valueOf(digit.toString())
        }
        return sum
    }


}