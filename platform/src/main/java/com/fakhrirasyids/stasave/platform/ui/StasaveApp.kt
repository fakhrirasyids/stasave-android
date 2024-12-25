package com.fakhrirasyids.stasave.platform.ui

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.di.viewModelModules
import com.fakhrirasyids.stasave.platform.ui.navigation.Screen
import com.fakhrirasyids.stasave.platform.ui.screens.main.MainScreen
import com.fakhrirasyids.stasave.platform.ui.screens.splash.SplashScreen
import org.koin.core.context.loadKoinModules

@Composable
fun StasaveApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    loadKoinModules(viewModelModules)

    StasaveTheme {
        NavHost(
            navController = navController,
            startDestination = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) Screen.Splash.route else Screen.Main.route
        ) {

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
