package com.uratxe.movetilt

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.lifecycle.SavedStateHandle
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.animelist.features.animelist.AnimeListViewModel
import com.uratxe.animelist.features.animelist.data.AnimeApiDataSource
import com.uratxe.animelist.features.animelist.data.AnimeDBDatasource
import com.uratxe.animelist.features.animelist.data.AnimeDataSource
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.uratxe.mvit.*
import com.uratxe.mvit.exception.Failure
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getStateViewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


abstract class KoinProyectActivity<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands, EventModel>,
        ModelData,ViewModelCommands,EventModel> : ProyectActivity<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewModel: ViewModel
        get()  {
            return getStateViewModel(getViewModelClass())
        }


}
abstract class ProyectActivity<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : MVVMIActivity<ViewModel, ModelData, ViewModelCommands, EventModel>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}

abstract class KoinProyectFragment<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : ProyectFragment<ViewModel,ModelData,ViewModelCommands,EventModel>(){

    override val viewModel: ViewModel by lazy {
        getStateViewModel(getViewModelClass())
    }

}
abstract class ProyectFragment<ViewModel : MVVMIViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : MVVMIFragment<ViewModel, ModelData, ViewModelCommands, EventModel>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}


class MainProyectViewDelegate  : MVVMIDelegate {
    override lateinit var context: Context


    override fun initViewDelegate(view: ViewGroup) {
        context = view.context
    }


    override fun processError(error: Failure) {
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
            modules(listOf(appModule,animeModule))
        }
    }

    val DB = "DB"
    val API = "API"

    val appModule = module {

        single { AuthModule() }
        viewModel { (handle: SavedStateHandle)  -> AnimeListViewModel(get(),handle,get()) }
    }

    val animeModule = module {
        single<AnimeDataSource>(named(API)) { AnimeApiDataSource() }
        single<AnimeDataSource>(named(DB)) { AnimeDBDatasource() }
        single { AnimeRepository(get(named(API)),get(named(DB))) }
    }


}

fun parametersActivity(activity: Activity): DefinitionParameters {
    return parametersOf(activity)
}