package com.uratxe.core.utils

import android.app.Activity
import com.google.firebase.analytics.FirebaseAnalytics

fun androidx.fragment.app.Fragment.analytics(screenName : String){
    activity?.let {
            verifiedActivity ->
        FirebaseAnalytics.getInstance(verifiedActivity).setCurrentScreen(verifiedActivity,screenName,
            verifiedActivity.javaClass.simpleName)
    }

}

fun Activity.analytics(screenName : String){
    FirebaseAnalytics.getInstance(this).setCurrentScreen(this,screenName,this.javaClass.simpleName)
}

