package com.uratxe.animelist.data

import com.auth0.android.jwt.JWT
import com.uratxe.mvit.PreferencesManager

class AuthModule {

    var jwt : JWT? = null

    private var accessToken : String = ""
    fun setAccessToken(accessToken : String){
        this.accessToken = accessToken;
        jwt = JWT(accessToken)
        PreferencesManager.setString("ACCESS_TOKEN",accessToken)
    }

    fun isAccessTokenValid(): Boolean {
        val accessToken = PreferencesManager.getString("ACCESS_TOKEN",null)
        if (!accessToken.isNullOrBlank()){
            setAccessToken(accessToken)
        }
        return (jwt != null && !jwt!!.isExpired(100))
    }
}