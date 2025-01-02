package com.fakhrirasyids.stasave.platform.utils.helper

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.fakhrirasyids.stasave.core.domain.model.MediaModel

class ExoPlayersPool(
    private val context: Context,
    private val pages: List<MediaModel>,
) {

    private val players = hashMapOf<Int, ExoPlayer>()

    init {
        prepareExoPlayers(pages)
    }

    private fun prepareExoPlayers(pages: List<MediaModel>) {
        pages.onEachIndexed { index, _ ->
            createAndGet(index)
        }
    }

    fun createAndGet(index: Int): ExoPlayer {
        val id = index % pages.size
        if (players.contains(id)) {
            return players[id]!!
        }

        val exoPlayer = ExoPlayer.Builder(context)
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_ALL
            }
        players[id] = exoPlayer
        return exoPlayer
    }

    fun releaseAll() {
        players.forEach {
            it.value.stop()
            it.value.release()
        }
        players.clear()
    }
}