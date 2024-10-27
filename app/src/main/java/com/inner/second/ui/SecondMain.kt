package com.inner.second.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.inner.second.navigation.SecondNavHost
import com.inner.second.ui.theme.SecondTheme

@Composable
fun SecondMain() {
    SecondTheme {
        val navHostController = rememberNavController()

        SecondNavHost(navHostController = navHostController)
    }
}