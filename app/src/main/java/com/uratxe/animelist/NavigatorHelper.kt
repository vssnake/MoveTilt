package com.uratxe.animelist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.uratxe.animelist.features.animelist.AnimeListFragment
import com.uratxe.animelist.features.main.AuthFragmentDirections
import com.uratxe.movetilt.R

object NavigatorHelper {


    fun launchAnimeList(navController: NavController){
        navController.navigate(AuthFragmentDirections.actionAuthFragmentToAnimeListFragment())
    }
}