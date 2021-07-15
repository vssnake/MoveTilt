package com.unatxe.mvvmi

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputLayout

@Deprecated("Use observer without anonyms functions", ReplaceWith("LifecycleOwner.observe(liveData: L, body: Observer<T>)", "androidx.lifecycle.Observer"))
fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: Observer<T>) =
    liveData.observe(this, body)