package com.fakhrirasyids.stasave.platform.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.BottomNavigationBar
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeScreen
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeViewModel
import com.fakhrirasyids.stasave.platform.ui.screens.main.saved.SavedScreen
import com.fakhrirasyids.stasave.platform.ui.screens.main.saved.SavedViewModel
import com.fakhrirasyids.stasave.platform.ui.screens.main.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToMediaPreview: ((Int, String, List<MediaModel>, Boolean) -> Unit)? = null,
) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val savedViewModel: SavedViewModel = koinViewModel()

    val whatsappUri by homeViewModel.whatsappUri
    val selectedMenuTitle =
        rememberSaveable { mutableStateOf(com.fakhrirasyids.stasave.platform.utils.enums.MainScreen.Home.name) }

    val context = LocalContext.current

    LaunchedEffect(whatsappUri) {
        if (whatsappUri.isNotEmpty()) {
            homeViewModel.getAllWhatsappImages(context)
            savedViewModel.getAllSavedImages(context)
            savedViewModel.getAllSavedVideos(context)
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedMenuTitle = selectedMenuTitle) }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            when (selectedMenuTitle.value) {
                com.fakhrirasyids.stasave.platform.utils.enums.MainScreen.Home.name -> {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToMediaPreview = { index, mediaType, mediaList ->
                            onNavigateToMediaPreview?.invoke(index, mediaType, mediaList, false)
                        }
                    )
                }

                com.fakhrirasyids.stasave.platform.utils.enums.MainScreen.Saved.name -> {
                    SavedScreen(
                        homeViewModel = homeViewModel,
                        savedViewModel = savedViewModel,
                        onNavigateToMediaPreview = { index, mediaType, mediaList ->
                            onNavigateToMediaPreview?.invoke(index, mediaType, mediaList, true)
                        }
                    )
                }

                com.fakhrirasyids.stasave.platform.utils.enums.MainScreen.Settings.name -> {
                    SettingsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    StasaveTheme {
        MainScreen()
    }
}