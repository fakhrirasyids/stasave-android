package com.fakhrirasyids.stasave.platform.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@Composable
fun TopBanner(
    modifier: Modifier = Modifier,
    title: String,
    onRefreshClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (title in listOf(MediaType.IMAGE.name.lowercase(), MediaType.VIDEO.name.lowercase())) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick?.invoke() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        if (title == MainScreen.Home.name) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = com.fakhrirasyids.stasave.common.R.drawable.ic_stasave_logo),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = when (title) {
                MediaType.IMAGE.name.lowercase() -> stringResource(id = com.fakhrirasyids.stasave.common.R.string.media_preview_detail_image)
                MediaType.VIDEO.name.lowercase() -> stringResource(id = com.fakhrirasyids.stasave.common.R.string.media_preview_detail_video)
                MainScreen.Home.name -> stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
                else -> title
            },
            color = if (title in listOf(
                    MediaType.IMAGE.name.lowercase(),
                    MediaType.VIDEO.name.lowercase()
                )
            ) Color.Companion.White else MaterialTheme.colorScheme.primary,
            fontWeight = if (title in listOf(
                    MediaType.IMAGE.name.lowercase(),
                    MediaType.VIDEO.name.lowercase()
                )
            ) FontWeight.Normal else FontWeight.ExtraBold,
            fontSize = if (title in listOf(
                    MediaType.IMAGE.name.lowercase(),
                    MediaType.VIDEO.name.lowercase()
                )
            ) 24.sp else 32.sp,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (title in listOf(MainScreen.Home.name, MainScreen.Saved.name)) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onRefreshClick?.invoke() },
                imageVector = Icons.Default.Refresh,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
            )
        }
    }
}
