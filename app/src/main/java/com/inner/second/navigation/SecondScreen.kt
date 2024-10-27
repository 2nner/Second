package com.inner.second.navigation

import kotlinx.serialization.Serializable


sealed interface SecondScreen {
    @Serializable
    data object Home : SecondScreen

    @Serializable
    data object Contract : SecondScreen
}