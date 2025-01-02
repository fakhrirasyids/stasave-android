package com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.media3.exoplayer.ExoPlayer
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import com.fakhrirasyids.stasave.platform.ui.components.MediaPreviewContent
import com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.MediaPreviewViewModel
import com.fakhrirasyids.stasave.platform.utils.constants.ExoPlayerConstants.rememberExoPlayersPool

@Composable
fun MediaPreviewPagerWidget(
    modifier: Modifier = Modifier,
    selectedMediaIndex: Int,
    mediaTypeName: String,
    mediaList: List<MediaModel>,
    viewModel: MediaPreviewViewModel
) {
    val pagerState = rememberPagerState(pageCount = { mediaList.size })

    val playersPool =
        rememberExoPlayersPool(if (mediaTypeName == MediaType.VIDEO.name.lowercase()) mediaList else listOf())
    val exoPlayer = remember { mutableStateOf<ExoPlayer?>(null) }
    var selectedPage by rememberSaveable { mutableIntStateOf(selectedMediaIndex) }

    LaunchedEffect(
        mediaList,
        pagerState
    ) {
        pagerState.scrollToPage(selectedMediaIndex)

        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (mediaTypeName == MediaType.VIDEO.name.lowercase()) {
                playersPool.createAndGet(selectedPage).pause()
            }
            selectedPage = page
        }
    }

    Column(
        modifier = modifier,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
        ) { selectedPageIndex ->
            if (mediaTypeName == MediaType.VIDEO.name.lowercase()) {
                exoPlayer.value = playersPool.createAndGet(selectedPageIndex)
            }

            MediaPreviewContent(
                modifier = modifier.fillMaxSize(),
                mediaModel = mediaList[selectedPageIndex],
                exoPlayer = exoPlayer.value,
                isPlaying = selectedPage == selectedPageIndex
            )
        }
    }
}