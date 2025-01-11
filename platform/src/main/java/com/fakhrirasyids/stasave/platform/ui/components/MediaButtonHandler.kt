package com.fakhrirasyids.stasave.platform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MediaButtonHandler(
    modifier: Modifier = Modifier,
    isFromSaved: Boolean = false,
    onDownloadClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransparentButton(
            icon = if (isFromSaved) Icons.Default.Delete else Icons.Default.Download,
            text = if (isFromSaved) {
                stringResource(com.fakhrirasyids.stasave.common.R.string.media_preview_detail_video_delete)
            } else {
                stringResource(com.fakhrirasyids.stasave.common.R.string.media_preview_detail_video_download)
            }
                    ,
            onClick = {
                if (isFromSaved)
                    onDeleteClick?.invoke()
                else
                    onDownloadClick?.invoke()
            }
        )

        TransparentButton(
            icon = Icons.Default.Share,
            text = stringResource(com.fakhrirasyids.stasave.common.R.string.media_preview_detail_video_share),
            onClick = { onShareClick?.invoke() }
        )
    }
}

@Composable
private fun TransparentButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White)
        }
    }
}