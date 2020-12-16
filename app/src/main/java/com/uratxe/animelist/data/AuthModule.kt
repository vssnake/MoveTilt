package com.uratxe.animelist.data

import com.auth0.android.jwt.JWT
import com.unatxe.mvvmi.PreferencesManager

class AuthModule {

    var jwt : JWT? = null

    private var accessToken : String = ""
    fun setAccessToken(accessToken : String){
        this.accessToken = accessToken;
        jwt = JWT(accessToken)
        com.unatxe.mvvmi.PreferencesManager.setString("ACCESS_TOKEN",accessToken)
    }

    fun isAccessTokenValid(): Boolean {
        val accessToken = com.unatxe.mvvmi.PreferencesManager.getString("ACCESS_TOKEN",null)
        if (!accessToken.isNullOrBlank()){
            setAccessToken(accessToken)
        }
        return (jwt != null && !jwt!!.isExpired(100))
    }
}