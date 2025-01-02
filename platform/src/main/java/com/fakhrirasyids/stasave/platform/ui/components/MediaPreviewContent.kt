package com.fakhrirasyids.stasave.platform.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import com.fakhrirasyids.stasave.platform.utils.constants.ExoPlayerConstants.handleError
import androidx.lifecycle.compose.LocalLifecycleOwner as LocalLifecycleOwner1

@Composable
fun MediaPreviewContent(
    modifier: Modifier = Modifier,
    mediaModel: MediaModel,
    exoPlayer: ExoPlayer? = null,
    isPlaying: Boolean = false
) {
    val context = LocalContext.current

    if (mediaModel.fileType == MediaType.IMAGE.name.lowercase()) {
        val imagePainter = rememberAsyncImagePainter(
            model = mediaModel.uri.toUri()
        )

        Image(
            painter = imagePainter,
            contentDescription = "Media Content",
            modifier = modifier,
        )
    } else {
        val playerView = remember {
            exoPlayer?.apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(mediaModel.uri))
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = isPlaying
                seekTo(0L)
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        handleError(error)
                    }
                })
            }

            PlayerView(context).apply {
                this.player = exoPlayer
            }
        }

        ComposableLifecycle { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer?.pause()
                }
                else -> {}
            }
        }

        DisposableEffect(exoPlayer) {
            onDispose {
                exoPlayer?.stop()
            }
        }

        Column {
            AndroidView(
                modifier = modifier,
                factory = {
                    playerView
                },
                update = { playerView ->
                    playerView.player = exoPlayer
                }
            )
        }
    }
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner1.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}