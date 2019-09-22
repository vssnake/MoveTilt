package com.uratxe.movetilt

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel,MainData,MainActivityCommands>() {


    override fun layoutId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override val viewDelegate = DerivedViewDelegate(super.viewDelegate)

    override fun setupViews() {

        am_retreive.setOnClickListener { viewModel.onCommandSend(MainActivityCommands.RetrieveUserCommand) }

        am_error.setOnClickListener { viewModel.onCommandSend(MainActivityCommands.ProcessErrorCommand) }
    }

    override fun onModelReceived(data: MainData) {
        am_email.text = data.email
        am_nick.text = data.nickName
    }



}


sealed class MainActivityCommands  {
    object RetrieveUserCommand : MainActivityCommands()
    object ProcessErrorCommand : MainActivityCommands()
}

