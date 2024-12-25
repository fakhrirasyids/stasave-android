package com.fakhrirasyids.stasave.platform.ui.screens.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1F else 0F,
        animationSpec = tween(durationMillis = 300), label = "SplashScreenAlphaAnimation"
    )

    LaunchedEffect(key1 = true) {
        delay(300)
        startAnimation = true
        delay(1700)
        startAnimation = false
        delay(500)
        navigateToHome()
    }

    Splash(modifier = modifier, alpha = alphaAnimation.value)
}

@Composable
fun Splash(
    modifier: Modifier,
    alpha: Float
) {
    StasaveTheme {
        Column (
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = modifier
                    .size(120.dp)
                    .alpha(alpha),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.ic_stasave_logo),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            )

            Text(
                modifier = modifier
                    .alpha(alpha),
                text = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashScreenPreview() {
    Splash(modifier = Modifier, alpha = 1f)
}