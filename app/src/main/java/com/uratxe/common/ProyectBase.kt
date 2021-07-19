package com.uratxe.common

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.unatxe.commons.data.exceptions.Failure
import org.koin.core.parameter.ParametersHolder
import org.koin.core.parameter.parametersOf
import com.unatxe.mvvmi.*
import com.uratxe.movetilt.R
import com.uratxe.movetilt.databinding.LytLoadingBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.concurrent.atomic.AtomicBoolean

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
    private lateinit var loadingBinding: LytLoadingBinding
    lateinit var handler : Handler

    override fun initViewDelegate(view: ViewGroup, fragmentDelegate: FragmentManager) {
        context = view.context
        handler = Handler(Looper.getMainLooper())
        inflateLoadingToView(view)
    }

    private fun inflateLoadingToView(view: ViewGroup) {
        val lytInflater = LayoutInflater.from(context)
        loadingBinding = LytLoadingBinding.inflate(lytInflater)
        view.addView(loadingBinding.root)
        Glide.with(context)
            .load(R.raw.loading)
            .into(loadingBinding.llLoading)
    }

    override fun processError(error: Failure) {
        Toast.makeText(context,R.string.default_error,Toast.LENGTH_SHORT).show()
    }

    private val isLoadingNeedToShow = AtomicBoolean(false)
    private fun loadingShow() : Runnable = Runnable {
        if (isLoadingNeedToShow.get()){
            loadingBinding.root.visibility = View.VISIBLE
            handler.postDelayed(loadingShow(),1000)
        }else{
            loadingBinding.root.visibility = View.GONE
        }
    }

    override fun showLoading(showLoading: Boolean) {
        isLoadingNeedToShow.set(showLoading)

        if (showLoading){
            handler.post(loadingShow())
        }
    }
}


fun parametersActivity(activity: Activity): ParametersHolder {
    return parametersOf(activity)
}