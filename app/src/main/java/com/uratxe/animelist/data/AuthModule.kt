package com.uratxe.animelist.data

import com.auth0.android.jwt.JWT

class AuthModule {

    var jwt : JWT? = null

    private var accessToken : String = ""
    fun setAccessToken(accessToken : String){
        this.accessToken = accessToken;
        jwt = JWT(accessToken)
    }

    fun isAccessTokenValid(): Boolean {
        return (jwt != null && !jwt!!.isExpired(100))
    }
}