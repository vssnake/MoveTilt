package com.uratxe.movetilt

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.animelist.features.animelist.AnimeListViewModel
import com.uratxe.mvit.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


abstract class KoinProyectActivity<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : ProyectActivity<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewModel: ViewModel
        get()  {
            return getViewModel(getViewModelClass())
        }


}
abstract class ProyectActivity<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : MVVMIActivity<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}

abstract class KoinProyectFragment<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : ProyectFragment<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewModel: ViewModel
        get()  {
            return getViewModel(getViewModelClass())
        }


}
abstract class ProyectFragment<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : MVVMIFragment<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}


class MainProyectViewDelegate  : MVVMIDelegate {
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


class ProyectApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin()

        PreferencesManager.init(this)
    }

    private fun initKoin(){
        startKoin{
            androidLogger()
            androidContext(this@ProyectApp)
            modules(appModule)
        }
    }

    val appModule = module {

        single { AuthModule() }


        viewModel { AnimeListViewModel(get()) }
    }
}

fun parametersActivity(activity: Activity): DefinitionParameters {
    return parametersOf(activity)
}