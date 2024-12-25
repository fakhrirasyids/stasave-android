package com.fakhrirasyids.stasave.platform.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.ui.components.BottomNavigationBar
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeScreen
import com.fakhrirasyids.stasave.platform.ui.screens.main.saved.SavedScreen
import com.fakhrirasyids.stasave.platform.ui.screens.main.settings.SettingsScreen
import com.fakhrirasyids.stasave.platform.utils.PlatformConstants

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val selectedMenuTitle = rememberSaveable { mutableStateOf(PlatformConstants.MainScreen.Home.name) }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedMenuTitle = selectedMenuTitle) }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            when (selectedMenuTitle.value) {
                PlatformConstants.MainScreen.Home.name -> {
                    HomeScreen()
                }

                PlatformConstants.MainScreen.Saved.name -> {
                    SavedScreen()
                }

                PlatformConstants.MainScreen.Settings.name -> {
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