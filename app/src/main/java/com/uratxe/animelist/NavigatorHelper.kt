package com.uratxe.animelist

import android.app.Activity
import android.content.Intent
import com.uratxe.animelist.features.animelist.AnimeListActivity

class NavigatorHelper(private val activity: Activity) {


    fun launchAnimeList(){
        activity.startActivity(Intent(activity,
            AnimeListActivity::class.java))
    }
}