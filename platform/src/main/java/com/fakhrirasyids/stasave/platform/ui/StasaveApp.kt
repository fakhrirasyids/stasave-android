package com.fakhrirasyids.stasave.platform.ui

import android.net.Uri
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.di.viewModelModules
import com.fakhrirasyids.stasave.platform.ui.navigation.Screen
import com.fakhrirasyids.stasave.platform.ui.screens.main.MainScreen
import com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.MediaPreviewScreen
import com.fakhrirasyids.stasave.platform.ui.screens.splash.SplashScreen
import com.fakhrirasyids.stasave.platform.utils.constants.AnimationConstants.slideComposable
import com.fakhrirasyids.stasave.platform.utils.helper.AssetParamType
import com.google.gson.Gson
import org.koin.core.context.loadKoinModules

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StasaveApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    loadKoinModules(viewModelModules)

    StasaveTheme {
        NavHost(
            modifier = modifier,
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
                MainScreen(
                    onNavigateToMediaPreview = { selectedItem, mediaTypeName, mediaList, isFromSaved ->
                        val mediaListUri = Uri.encode(Gson().toJson(mediaList))
                        navController.navigate(
                            Screen.MediaPreview.createRoute(
                                selectedItem,
                                mediaTypeName,
                                mediaListUri,
                                isFromSaved
                            )
                        )
                    }
                )
            }

            // MediaPreview Route
            slideComposable(
                route = Screen.MediaPreview.route,
                arguments = listOf(
                    navArgument(
                        Screen.EXTRA_MEDIA_PREVIEW_SELECTED_ITEM_INDEX,
                    ) { type = NavType.IntType },
                    navArgument(
                        Screen.EXTRA_MEDIA_PREVIEW_TYPE_NAME,
                    ) { type = NavType.StringType },
                    navArgument(Screen.EXTRA_MEDIA_PREVIEW_LIST) {
                        type = AssetParamType()
                    },
                    navArgument(
                        Screen.EXTRA_MEDIA_IS_FROM_SAVED,
                    ) { type = NavType.BoolType },
                )
            ) { backStackEntry ->
                val selectedItemIndex =
                    backStackEntry.arguments?.getInt(Screen.EXTRA_MEDIA_PREVIEW_SELECTED_ITEM_INDEX)
                        ?: 0
                val mediaTypeName =
                    backStackEntry.arguments?.getString(Screen.EXTRA_MEDIA_PREVIEW_TYPE_NAME) ?: ""
                val isFromSaved =
                    backStackEntry.arguments?.getBoolean(Screen.EXTRA_MEDIA_IS_FROM_SAVED) ?: false
                val mediaList =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        backStackEntry.arguments?.getParcelableArrayList(
                            Screen.EXTRA_MEDIA_PREVIEW_LIST,
                            MediaModel::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        backStackEntry.arguments?.getParcelableArrayList(
                            Screen.EXTRA_MEDIA_PREVIEW_LIST
                        )
                    }?.toList() ?: listOf()

                MediaPreviewScreen(
                    selectedItemIndex = selectedItemIndex,
                    mediaTypeName = mediaTypeName,
                    mediaList = mediaList,
                    isFromSaved = isFromSaved,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}
