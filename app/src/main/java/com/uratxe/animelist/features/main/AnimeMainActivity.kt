package com.uratxe.animelist.features.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.uratxe.animelist.NavigatorHelper
import com.uratxe.animelist.data.AuthModule
import com.uratxe.movetilt.R
import kotlinx.android.synthetic.main.activity_anime_main.*
import org.koin.android.ext.android.inject

class AnimeMainActivity : AppCompatActivity() {


    val authModule : AuthModule by inject()

    lateinit var navController : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_main)
        navController = anm_navigator as NavHostFragment
        isAccessTokenValid()
    }

    private fun isAccessTokenValid(){

        if (authModule.isAccessTokenValid()){
            NavigatorHelper.launchAnimeList(navController.navController)
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
            NavigatorHelper.launchAnimeList(navController.navController)
        }

    }
}
