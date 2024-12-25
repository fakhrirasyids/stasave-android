package com.fakhrirasyids.stasave.platform.ui.screens.main.widgets

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.MediaGridItem

@Composable
fun MediaGridWidget(
    mediaList: List<MediaModel>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
    ) {
        items(mediaList) { mediaModel ->
            MediaGridItem(mediaModel = mediaModel)
        }
    }
}