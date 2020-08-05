package com.uratxe.core.utils

fun UByte.changeBit(position : Int, isTrue : Boolean) : UByte{
    return if (isTrue){
        this or (1 shl position).toUByte()
    }else{
        this and (1 shl position).inv().toUByte()
    }
}

fun Int.checkBit(position : Int) : Boolean{
    return (this shr position and 1) == 1
}