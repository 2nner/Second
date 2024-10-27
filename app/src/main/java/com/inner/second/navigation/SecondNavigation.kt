package com.inner.second.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.inner.second.feature.home.SecondHome

fun NavGraphBuilder.secondNavigation() {
    composable<SecondScreen.Home> {
        SecondHome()
    }
}