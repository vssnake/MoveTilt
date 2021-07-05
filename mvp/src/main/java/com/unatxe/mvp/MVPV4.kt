package com.unatxe.mvp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import java.lang.ref.WeakReference


abstract class BaseActivityV4 : LocalizationActivity(), BaseContractV4 {

    var dataBundle : Bundle? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dataBundle?.let {
            outState.putAll(dataBundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            dataBundle = intent.extras
        } else if (savedInstanceState != null) {
            dataBundle = savedInstanceState
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.extras != null) {
            dataBundle = intent.extras
        }
    }

    open fun getViewDelegate() : ViewDelegateV4?{
        return  null
    }

    override fun getContext(): Context {
        return this
    }

    open fun getActivityDelegate() : ActivityDelegateV3?{
        return null
    }

    open fun setupViews(){}


    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        setupViews()
        getViewDelegate()?.currentMainContainerSet(findViewById<View>(android.R.id.content))
    }


    override fun attachBaseContext(newBase: Context) {
        if (getActivityDelegate() != null){
            super.attachBaseContext(getActivityDelegate()!!.attachBaseContext(newBase!!))
        }else{
            super.attachBaseContext(newBase)
        }

    }

    override fun loading(loading: Boolean) {
        getViewDelegate()?.loading(loading)
    }

    override fun onError(error: String?, typeError: ViewDelegateV4.TypeError, throwable: Throwable?, keyError: String) {
        var stringError: String? = null
        if (error != null && !error.isEmpty()) {
            stringError = error
        } else if (throwable != null) {
            stringError = throwable.message
        }
        getViewDelegate()?.launchError(stringError,typeError,keyError,throwable,buttonErrorCallback)
    }

    val buttonErrorCallback = object : ViewDelegateV4.ButtonErrorCallback{
        override fun ButtonErrorPressed(typeError: ViewDelegateV4.TypeError, keyError: String) {
            onErrorButtonClicked(typeError,keyError)
        }
    }

    abstract fun onErrorButtonClicked(typeError: ViewDelegateV4.TypeError,keyError: String)

    override fun onSuccess() {
        getViewDelegate()?.onSuccess()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            checkFinishMethod()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    override fun getBundle(): Bundle? {
        return if (dataBundle != null){
            dataBundle
        }else{
            intent.extras
        }
    }

    override fun onBackPressed() {
        checkFinishMethod()
    }

    override fun getArguments(): Bundle? {
        return null
    }
}

abstract class BaseFragmentV4 : androidx.fragment.app.Fragment(), BaseContractV4{

    open fun setupViews(){}

    override fun finish() {
        activity?.finish()
    }

    abstract fun getFragmentDelegate() : FragmentDelegateV3?


    open fun getViewDelegate() : ViewDelegateV4?{
        return  null
    }

    override fun onError(error: String?, typeError: ViewDelegateV4.TypeError, throwable: Throwable?, keyError: String) {
        var stringError: String? = null
        if (error != null && error.isNotBlank()) {
            stringError = error
        } else if (throwable != null) {
            stringError = throwable.message
        }
        getViewDelegate()?.launchError(stringError,typeError,keyError,throwable,buttonErrorCallback)
    }

    val buttonErrorCallback = object : ViewDelegateV4.ButtonErrorCallback{
        override fun ButtonErrorPressed(typeError: ViewDelegateV4.TypeError, keyError: String) {
           onErrorButtonClicked(typeError,keyError)
        }
    }

    abstract fun onErrorButtonClicked(typeError: ViewDelegateV4.TypeError,keyError: String)


    override fun loading(loading: Boolean) {
        getViewDelegate()?.loading(loading)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentDelegate()?.injectLifeCycle(lifecycle)
        getViewDelegate()?.injectLifeCycle(lifecycle)
        if (view is ViewGroup) {
            getViewDelegate()?.currentMainContainerSet(view)
        }
        setupViews()

    }

    override fun getBundle(): Bundle? {
        activity?.let {
            if (activity is BaseActivityV3){
                return (activity as BaseActivityV3).getBundle()
            }
        }
        return null
    }

}

abstract class MVPFragmentV4<out T : BasePresenterV4<out BaseContractV4>> : BaseFragmentV4(), BaseContractV4 {


    abstract fun injectDI()

    abstract fun getPresenter(): T?

    override fun onSuccess() {
        getViewDelegate()?.onSuccess()
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPresenter()?.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        getPresenter()?.onCreate(savedInstanceState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        getPresenter()?.onLowMemory()
    }

    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getPresenter()?.onDestroyView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getPresenter()?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getFragmentDelegate()?.injectLifeCycle(lifecycle)
        super.onViewCreated(view, savedInstanceState)
        getPresenter()?.injectLifeCycle(lifecycle)
        getPresenter()?.onViewCreated()
    }

    override fun onErrorButtonClicked(typeError: ViewDelegateV4.TypeError, keyError: String) {
       getPresenter()?.onErrorButtonClicked(typeError,keyError)
    }
}

abstract class MVPActivityV4<out T : BasePresenterV4<out BaseContractV4>> : BaseActivityV4(),BaseContractV4 {
    override fun getContext(): Context {
        return this
    }

    init{
        injectDI()
        getPresenter()?.injectLifeCycle(lifecycle)
        getViewDelegate()?.injectLifeCycle(lifecycle)
        getActivityDelegate()?.injectLifeCycle(lifecycle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getPresenter()?.onSaveInstanceState(outState)
    }

    abstract fun injectDI()

    abstract fun getPresenter(): T?

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getPresenter()?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenter()?.onCreate(savedInstanceState)
    }

    override fun onErrorButtonClicked(typeError: ViewDelegateV4.TypeError, keyError: String) {
        getPresenter()?.onErrorButtonClicked(typeError,keyError)
    }
}

abstract class BasePresenterV4<T : BaseContractV4> : LifecycleObserver{
    abstract fun loadData()



    var lifeCycle : Lifecycle? = null

    fun injectLifeCycle(lifeCycle: Lifecycle){
        this.lifeCycle = lifeCycle
        this.lifeCycle!!.addObserver(this)
    }


    open fun onCreate(savedInstanceState: Bundle?) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(){
        lifeCycle?.removeObserver(this)
        viewInterface = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(){}

    private var viewInterface: WeakReference<T?>? = null

    fun getViewContract() : T?{
        return viewInterface?.get()
    }

    fun inject(contract : T){
        viewInterface = WeakReference(contract)
    }





    @CallSuper
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MvpUtils.ERROR_REQUEST_CODE && resultCode == Activity.RESULT_OK) { }
        if (requestCode == MvpUtils.SUCESS_ACTIVITY_RESULT && resultCode == Activity.RESULT_OK) {
            getViewContract()?.finish()
        }
    }

    open fun onAttach(context: Context?){}
    open fun onLowMemory(){}
    open fun onActivityCreated(savedInstanceState: Bundle?){}
    open fun onDetach(){}
    open fun onDestroyView(){
        //LifeCycle of fragments
        onDestroy()
    }
    open fun onSaveInstanceState(outState: Bundle?){}
    open fun onViewCreated(){}

    abstract fun onErrorButtonClicked(typeError: ViewDelegateV4.TypeError,keyError: String)


}



interface BaseContractV4{
    fun onSuccess()

    fun getContext(): Context?

    fun loading(loading: Boolean)

    fun onError(error: String?, typeError : ViewDelegateV4.TypeError, throwable: Throwable?, keyError: String = "")

    fun finish()

    fun getBundle() : Bundle?

    fun getArguments() : Bundle?

}

abstract class ViewDelegateV4(val activity: WeakReference<Activity>) : LifecycleObserver {

    enum class TypeError { ERROR_RETRY, ERROR_OK }

    public interface ButtonErrorCallback{
        fun  ButtonErrorPressed(typeError: TypeError,keyError: String)
    }

    abstract fun launchError(stringError: String?,typeError : TypeError,keyError: String,throwable: Throwable?, buttonErrorCallback: ButtonErrorCallback)

    var mainContainer : View? = null
        private set

    abstract fun loading(loading: Boolean)
    @CallSuper
    open fun currentMainContainerSet(view:View?){
        mainContainer = view
    }
    abstract fun onSuccess()

    var lifeCycle : Lifecycle? = null

    fun injectLifeCycle(lifecycle: Lifecycle) {
        this.lifeCycle = lifecycle
        this.lifeCycle!!.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(){
        lifeCycle?.removeObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause(){}

}