package com.inner.second.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SecondNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = SecondScreen.Home,
    ) {
        secondNavigation()
    }
}