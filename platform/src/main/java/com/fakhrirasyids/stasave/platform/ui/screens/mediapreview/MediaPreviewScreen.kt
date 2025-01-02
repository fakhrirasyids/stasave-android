package com.fakhrirasyids.stasave.platform.ui.screens.mediapreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.widgets.MediaPreviewPagerWidget
import org.koin.androidx.compose.koinViewModel

@Composable
fun MediaPreviewScreen(
    modifier: Modifier = Modifier,
    viewModel: MediaPreviewViewModel = koinViewModel(),
    selectedItemIndex: Int,
    mediaTypeName: String,
    mediaList: List<MediaModel>,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier.fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding),
        ) {
            TopBanner(
                title = mediaTypeName,
                onBackClick = {
                    onBackClick.invoke()
                }
            )

            MediaPreviewPagerWidget(
                modifier = modifier.weight(1f),
                selectedMediaIndex = selectedItemIndex,
                mediaTypeName = mediaTypeName,
                mediaList = mediaList,
                viewModel = viewModel
            )
        }
    }
}