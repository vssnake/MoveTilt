package com.uratxe.movetilt

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.SavedStateHandle
import com.unatxe.mvvmi.MVVMIActivity
import com.unatxe.mvvmi.MVVMIDelegate
import com.unatxe.mvvmi.MVVMIFragment
import com.unatxe.mvvmi.MVVMIViewModel
import com.uratxe.animelist.data.AuthModule
import com.uratxe.animelist.features.animelist.AnimeListViewModel
import com.uratxe.animelist.features.animelist.data.AnimeApiDataSource
import com.uratxe.animelist.features.animelist.data.AnimeDBDatasource
import com.uratxe.animelist.features.animelist.data.AnimeDataSource
import com.uratxe.animelist.features.animelist.data.AnimeRepository
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.PreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module


abstract class KoinProyectActivity<ViewModel : MVVMIViewModel<ModelData>,
        ModelData> : ProyectActivity<ViewModel,ModelData>(){

    override val viewModel: ViewModel
        get()  {
            return getStateViewModel(clazz = getViewModelClass())
        }
}
abstract class ProyectActivity<ViewModel : MVVMIViewModel<ModelData>,
        ModelData> : MVVMIActivity<ViewModel, ModelData>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}

abstract class KoinProyectFragment<ViewModel : MVVMIViewModel<ModelData>,
        ModelData> : ProyectFragment<ViewModel,ModelData>(){

    override val viewModel: ViewModel by stateViewModel(clazz = getViewModelClass())
}

abstract class ProyectFragment<ViewModel : MVVMIViewModel<ModelData>,
        ModelData> : MVVMIFragment<ViewModel, ModelData>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()
}


class MainProyectViewDelegate  : MVVMIDelegate {
    override lateinit var context: Context

    override fun initViewDelegate(view: ViewGroup, fragmentDelegate: FragmentManager) {
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
        startKoin {
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

fun parametersActivity(activity: Activity): ParametersHolder {
    return parametersOf(activity)
}