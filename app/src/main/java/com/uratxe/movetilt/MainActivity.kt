package com.uratxe.movetilt

import com.uratxe.mvit.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ProyectActivity<MainViewModel, MainData, MainViewEvent>() {


    override fun layoutId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override val viewDelegate = DerivedViewDelegate(super.viewDelegate)

    override fun setupViews() {

        am_retreive.setOnClickListener { viewModel.onEvent(MainViewEvent.RetrieveUserEvent) }

        am_error.setOnClickListener { viewModel.onEvent(MainViewEvent.ProcessErrorEvent("025 Error")) }
    }

    override fun onModelReceived(data: MainData) {
        am_email.text = data.email
        am_nick.text = data.nickName
    }





}


sealed class MainViewEvent  {
    object RetrieveUserEvent : MainViewEvent()
    data class ProcessErrorEvent(val typeError : String) : MainViewEvent()
}

