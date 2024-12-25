package com.fakhrirasyids.stasave.platform.ui.navigation

import com.fakhrirasyids.stasave.platform.utils.PlatformConstants

sealed class Screen(val route: String) {
    data object Splash : Screen(PlatformConstants.PlatformScreen.SPLASH.name.lowercase())
    data object Main : Screen(PlatformConstants.PlatformScreen.MAIN.name.lowercase())
}