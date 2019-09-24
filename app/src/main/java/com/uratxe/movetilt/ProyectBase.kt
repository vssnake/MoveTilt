package com.uratxe.movetilt

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.uratxe.mvit.BaseActivity
import com.uratxe.mvit.BaseViewDelegate
import com.uratxe.mvit.BaseViewModel

abstract class ProyectActivity<ViewModel : BaseViewModel<ModelData, ViewModelCommands>,
        ModelData,ViewModelCommands> : BaseActivity<ViewModel,ModelData,ViewModelCommands>(){

    override val viewDelegate: BaseViewDelegate = MainProyectViewDelegate()

}

class MainProyectViewDelegate  : BaseViewDelegate {
    override lateinit var context: Context


    override fun initViewDelegate(view: ViewGroup) {
        context = view.context
    }


    override fun processError(error: Throwable) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading(boolean: Boolean) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}