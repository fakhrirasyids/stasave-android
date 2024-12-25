package com.fakhrirasyids.stasave.platform.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.stasave.platform.utils.PlatformConstants

@Composable
fun TopBanner(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (title == PlatformConstants.MainScreen.Home.name) {
            Icon(
                modifier = modifier
                    .size(40.dp),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.ic_stasave_logo),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            )

            Spacer(modifier = modifier.padding(6.dp))
        }

        Text(
            modifier = modifier,
            text = if (title == PlatformConstants.MainScreen.Home.name) {
                stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            } else {
                title
            },
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
        )
    }
}