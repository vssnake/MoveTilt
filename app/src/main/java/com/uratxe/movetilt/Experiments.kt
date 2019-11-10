package com.uratxe.movetilt

import android.widget.Toast
import com.uratxe.mvit.MVVMIDelegate







class DerivedViewDelegate(b: MVVMIDelegate) : MVVMIDelegate by b{
    override fun processError(error: Throwable) {
        Toast.makeText(context,error.message,Toast.LENGTH_LONG ).show()
    }

    override fun showLoading(boolean: Boolean) {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}