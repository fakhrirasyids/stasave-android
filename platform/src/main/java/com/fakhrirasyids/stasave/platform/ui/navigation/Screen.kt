package com.fakhrirasyids.stasave.platform.ui.navigation

import com.fakhrirasyids.stasave.platform.utils.enums.PlatformScreen

sealed class Screen(val route: String) {
    data object Splash : Screen(PlatformScreen.SPLASH.name.lowercase())
    data object Main : Screen(PlatformScreen.MAIN.name.lowercase())
}