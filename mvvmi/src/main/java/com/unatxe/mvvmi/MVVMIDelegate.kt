package com.unatxe.mvvmi

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.unatxe.commons.data.exceptions.Failure

interface MVVMIDelegate {

    var context : Context

    fun processError(error: Failure)
    fun showLoading(boolean: Boolean)
    fun initViewDelegate(view : ViewGroup, fragmentDelegate : FragmentManager)
}