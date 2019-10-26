package com.uratxe.movetilt.main

import com.uratxe.movetilt.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : ProyectActivity<MainViewModel, MainData, MainViewEvent, MainModelEvent>() {

    override fun layoutId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class

    override val viewDelegate = DerivedViewDelegate(super.viewDelegate)

    override fun setupViews() {
        am_retreive.setOnClickListener { viewModel.onEventFromView(MainViewEvent.RetrieveUserEvent) }
        am_error.setOnClickListener { viewModel.onEventFromView(
            MainViewEvent.ProcessErrorEvent("025 Error")
        )}
    }

    override fun onModelReceived(data: MainData) {
        am_email.text = data.email
        am_nick.text = data.nickName
    }

    override fun onEventModelReceived(data: MainModelEvent) {
        when(data){
            is MainModelEvent.LaunchActivity -> { }
            is MainModelEvent.StartStopLocation -> {  }
        }
    }
}
