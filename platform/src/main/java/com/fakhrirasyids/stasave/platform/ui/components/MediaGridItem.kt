package com.fakhrirasyids.stasave.platform.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.utils.enums.MediaType

@Composable
fun MediaGridItem(
    mediaModel: MediaModel
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components { add(VideoFrameDecoder.Factory()) }
        .build()

    val imagePainter = if (mediaModel.fileType == MediaType.VIDEO.name.lowercase()) {
        rememberAsyncImagePainter(
            model = mediaModel.uri.toUri(),
            imageLoader = imageLoader
        )
    } else {
        rememberAsyncImagePainter(
            model = mediaModel.uri.toUri()
        )
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = imagePainter,
                contentDescription = "Media Preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = imagePainter,
                contentDescription = "Media Preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            if (mediaModel.fileType == MediaType.VIDEO.name.lowercase()) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play Video",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}