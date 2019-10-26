package com.uratxe.movetilt

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.uratxe.animelist.AuthActivity
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.animelist.features.animelist.AnimeListViewModel
import com.uratxe.mvit.BaseActivity
import com.uratxe.mvit.BaseLiveData
import com.uratxe.mvit.BaseViewDelegate
import com.uratxe.mvit.BaseViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.reflect.KClass


abstract class KoinProyectActivity<ViewModel : BaseViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : ProyectActivity<ViewModel,ModelData,ViewModelCommands,EventModel>(){






    override val viewModel: ViewModel
        get()  {
            return getViewModel(getViewModelClass())
        }


}
abstract class ProyectActivity<ViewModel : BaseViewModel<ModelData, ViewModelCommands,EventModel>,
        ModelData,ViewModelCommands,EventModel> : BaseActivity<ViewModel,ModelData,ViewModelCommands,EventModel>(){

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


class ProyectApp : Application(){

    override fun onCreate() {
        super.onCreate()
        initKoin()
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


        factory {(activity : Activity) ->
            NavigatorHelper(activity) }

        //viewModel { AnimeListViewModel(get(),get(),get()) }
    }
}

fun parametersActivity(activity: Activity): DefinitionParameters {
    return parametersOf(activity)
}