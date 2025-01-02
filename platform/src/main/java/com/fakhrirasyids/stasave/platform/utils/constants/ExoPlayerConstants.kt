package com.fakhrirasyids.stasave.platform.utils.constants

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.PlaybackException
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.utils.helper.ExoPlayersPool

object ExoPlayerConstants {
    @Composable
    fun rememberExoPlayersPool(
        pages: List<MediaModel> = listOf()
    ): ExoPlayersPool {
        val context = LocalContext.current
        val playersPool = remember { ExoPlayersPool(context, pages) }

        DisposableEffect(Unit) {
            onDispose {
                playersPool.releaseAll()
            }
        }

        return playersPool
    }

    fun handleError(error: PlaybackException) {
        when (error.errorCode) {
            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                println("File not found")
            }

            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> {
                println("Decoder initialization error")
            }

            else -> {
                println("Other error: ${error.message}")
            }
        }
    }
}