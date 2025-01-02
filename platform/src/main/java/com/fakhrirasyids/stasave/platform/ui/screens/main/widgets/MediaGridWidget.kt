package com.fakhrirasyids.stasave.platform.ui.screens.main.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.MediaGridItem

@Composable
fun MediaGridWidget(
    mediaList: List<MediaModel>,
    modifier: Modifier = Modifier,
    onNavigateToMediaPreview: ((Int, String) -> Unit)? = null,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(mediaList) { index, mediaModel ->
            MediaGridItem(
                modifier = modifier
                    .clickable {
                        onNavigateToMediaPreview?.invoke(
                            index,
                            mediaModel.fileType
                        )
                    },
                mediaModel = mediaModel
            )
        }
    }
}