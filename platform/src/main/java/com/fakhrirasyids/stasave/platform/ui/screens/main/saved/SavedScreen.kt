package com.fakhrirasyids.stasave.platform.ui.screens.main.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.ui.components.PermissionComposable
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBanner(title = MainScreen.Saved.name)

//        PermissionComposable()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SavedScreenPreview() {
    StasaveTheme {
        SavedScreen()
    }
}