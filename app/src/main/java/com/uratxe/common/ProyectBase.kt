package com.uratxe.common

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.unatxe.commons.data.exceptions.Failure
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import com.unatxe.mvvmi.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class KoinProyectActivity<ViewModel : MVVMIViewModel<ModelData>, ModelData>
    : ProyectActivity<ViewModel, ModelData>() where ModelData: MVVMIData{

    override val viewModel: ViewModel
        get()  {
            return getViewModel(
                clazz = getViewModelClass(),
                parameters =  { parametersOf(this as Activity) }
            )
        }
}
abstract class ProyectActivity<ViewModel : MVVMIViewModel<ModelData>,
        ModelData : MVVMIData> : MVVMIActivity<ViewModel, ModelData>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()

}

abstract class KoinProyectFragment<ViewModel : MVVMIViewModel<ModelData>,
        ModelData : MVVMIData> : ProyectFragment<ViewModel, ModelData>(){

    override val viewModel: ViewModel
        get(){
            return getViewModel(
                clazz = getViewModelClass(),
                parameters =  { parametersOf(requireActivity() as Activity) }
            )
        }
}

abstract class ProyectFragment<ViewModel : MVVMIViewModel<ModelData>,
        ModelData : MVVMIData> : MVVMIFragment<ViewModel, ModelData>(){

    override val viewDelegate: MVVMIDelegate = MainProyectViewDelegate()
}


class MainProyectViewDelegate: MVVMIDelegate {
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


fun parametersActivity(activity: Activity): ParametersHolder {
    return parametersOf(activity)
}