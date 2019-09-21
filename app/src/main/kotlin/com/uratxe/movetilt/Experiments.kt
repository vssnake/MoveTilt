package com.uratxe.movetilt

import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class MainViewDelegate  : BaseViewDelegate{
    override fun initViewDelegate(view: ViewGroup) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun processError(error: Throwable) {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading(boolean: Boolean) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


interface BaseViewDelegate {
    fun processError(error: Throwable)
    fun showLoading(boolean: Boolean)
    fun initViewDelegate(view : ViewGroup)
}


class DerivedViewDelegate(b: BaseViewDelegate) : BaseViewDelegate by b{
    override fun processError(error: Throwable) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading(boolean: Boolean) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}