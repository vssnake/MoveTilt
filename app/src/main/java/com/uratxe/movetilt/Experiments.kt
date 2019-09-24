package com.uratxe.movetilt

import android.view.ViewGroup
import android.widget.Toast
import com.uratxe.mvit.BaseViewDelegate







class DerivedViewDelegate(b: BaseViewDelegate) : BaseViewDelegate by b{
    override fun processError(error: Throwable) {
        Toast.makeText(context,error.message,Toast.LENGTH_LONG ).show()
    }

    override fun showLoading(boolean: Boolean) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}