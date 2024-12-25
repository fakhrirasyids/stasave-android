package com.fakhrirasyids.stasave.platform.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.ui.navigation.Screen
import com.fakhrirasyids.stasave.platform.ui.screens.main.MainScreen
import com.fakhrirasyids.stasave.platform.ui.screens.splash.SplashScreen

@Composable
fun StasaveApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    StasaveTheme {
        NavHost(navController = navController, startDestination = Screen.Splash.route) {

            // Splash Route
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    navigateToHome = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            // Home Route
            composable(route = Screen.Main.route) {
                MainScreen()
            }
        }
    }
}
