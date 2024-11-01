package com.inner.second.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.inner.second.core.navigation.SecondScreen

@Composable
fun SecondNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = SecondScreen.Contract,
    ) {
        secondNavigation(navController = navHostController)
    }
}