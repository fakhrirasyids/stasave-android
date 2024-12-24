package com.fakhrirasyids.stasave.platform.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fakhrirasyids.stasave.common.theme.StasaveTheme

@Composable
fun StasaveApp() {
    StasaveTheme {
        Greeting("Stasave")
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StasaveTheme {
        Greeting("Android")
    }
}