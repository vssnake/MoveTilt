package com.uratxe.animelist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uratxe.animelist.data.AuthModule
import com.uratxe.movetilt.R
import com.uratxe.movetilt.parametersActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class AuthActivity : AppCompatActivity() {

    val authModule : AuthModule by inject()
    val navigatorHelper : NavigatorHelper by inject{ parametersActivity(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://anilist.co/api/v2/oauth/authorize?client_id=2694&response_type=token"))
            startActivity(intent)

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.data?.toString()?.split("#access_token=")?.let{
            if (it.size >= 2){
                authModule.setAccessToken(it[1])
            }
        }
        if (authModule.isAccessTokenValid()){
            navigatorHelper.launchAnimeList()
        }

    }
}
