package com.unatxe.mvvmi

import com.unatxe.commons.data.exceptions.Failure

sealed class MVVMILiveData<Data>{

    class Error<Data>(val failure : Failure) : MVVMILiveData<Data>()

    class Loading<Data>(val loading : Boolean) : MVVMILiveData<Data>()

    data class TypeData<Data>(val data : Data) : MVVMILiveData<Data>()

}