package com.fakhrirasyids.stasave.platform.ui.screens.main.widgets

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.ErrorContent
import com.fakhrirasyids.stasave.platform.utils.enums.HomeSavedTabs
import kotlinx.coroutines.launch

@Composable
fun MediaPagerWidget(
    documentTreeLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    modifier: Modifier = Modifier,
    whatsappImageMedias: List<MediaModel>,
    whatsappVideoMedias: List<MediaModel>,
    isLoading: Boolean,
    isErrorMessage: String,
    isFromSaved: Boolean = false,
    onNavigateToMediaPreview: ((Int, String, List<MediaModel>) -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeSavedTabs.entries.size })
    val selectedPageIndex = rememberSaveable { mutableIntStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState.currentPage) {
        selectedPageIndex.intValue = pagerState.currentPage
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TabRow(
            selectedTabIndex = selectedPageIndex.intValue,
            modifier = modifier
                .fillMaxWidth()
        ) {
            HomeSavedTabs.entries.forEachIndexed { index, tab ->
                Tab(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.background),
                    selected = index == selectedPageIndex.intValue,
                    onClick = {
                        scope.launch {
                            selectedPageIndex.intValue = index
                            pagerState.animateScrollToPage(tab.ordinal)
                        }
                    },
                    text = { Text(text = tab.name) }
                )
            }
        }

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 10.dp
                )
            } else if (isErrorMessage.isNotEmpty()) {
                ErrorContent(
                    errorMessage = isErrorMessage,
                    documentTreeLauncher = documentTreeLauncher
                )
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = modifier
                        .weight(1F)
                ) { page ->
                    val mediaList = if (page == 0) {
                        whatsappImageMedias
                    } else {
                        whatsappVideoMedias
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (mediaList.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = modifier
                                        .size(140.dp),
                                    imageVector = Icons.Filled.SentimentVeryDissatisfied,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = stringResource(id = com.fakhrirasyids.stasave.common.R.string.app_name)
                                )

                                Text(
                                    text = if (isFromSaved) {
                                        stringResource(id = com.fakhrirasyids.stasave.common.R.string.saved_media_pager_empty)
                                    } else {
                                        stringResource(id = com.fakhrirasyids.stasave.common.R.string.home_media_pager_empty)
                                    },
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        } else {
                            MediaGridWidget(
                                modifier = modifier,
                                mediaList = mediaList,
                                onNavigateToMediaPreview = { index, mediaType ->
                                    onNavigateToMediaPreview?.invoke(index, mediaType, mediaList)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}