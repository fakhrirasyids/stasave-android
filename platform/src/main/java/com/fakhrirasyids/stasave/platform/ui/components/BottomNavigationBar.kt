package com.fakhrirasyids.stasave.platform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fakhrirasyids.stasave.platform.data.models.BottomBarItem
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedMenuTitle: MutableState<String>
) {
    Column {
        Spacer(
            modifier = modifier
                .background(MaterialTheme.colorScheme.onSurface)
                .fillMaxWidth()
                .height(1.dp)
        )

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier
        ) {
            val navigationItems = listOf(
                BottomBarItem(
                    title = MainScreen.Home.name,
                    icon = Icons.Default.Home
                ),
                BottomBarItem(
                    title = MainScreen.Saved.name,
                    icon = Icons.Default.Menu
                ),
                BottomBarItem(
                    title = MainScreen.Settings.name,
                    icon = Icons.Default.Settings
                ),
            )

            navigationItems.map {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title,
                        )
                    },
                    label = {
                        Text(it.title)
                    },
                    selected = it.title == selectedMenuTitle.value,
                    onClick = {
                        selectedMenuTitle.value = it.title
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                        selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                        unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }
        }
    }
}