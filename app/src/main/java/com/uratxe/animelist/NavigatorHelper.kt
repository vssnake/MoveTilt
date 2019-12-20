package com.uratxe.animelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.navigation.*
import com.uratxe.animelist.features.animelist.AnimeListFragment
import com.uratxe.animelist.features.main.AuthFragmentDirections
import com.uratxe.movetilt.R

object NavigatorHelper {


    fun launchAnimeList(navController: NavController) {
        navigate(navController,AuthFragmentDirections.actionAuthFragmentToAnimeListFragment())
    }

    private fun navigate(navController: NavController,navDirections: NavDirections) {
        navController.navigate(navDirections)
    }
}