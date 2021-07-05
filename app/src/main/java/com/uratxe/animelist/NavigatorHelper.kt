package com.uratxe.animelist

import androidx.navigation.NavController
import androidx.navigation.NavDirections

object NavigatorHelper {


    fun launchAnimeList(navController: NavController) {
      //  navigate(navController,AuthFragmentDirections.actionAuthFragmentToAnimeListFragment())
    }

    private fun navigate(navController: NavController,navDirections: NavDirections) {
        navController.navigate(navDirections)
    }
}