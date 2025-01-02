package com.fakhrirasyids.stasave.platform.ui.screens.mediapreview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.ui.screens.mediapreview.widgets.MediaPreviewPagerWidget
import com.fakhrirasyids.stasave.platform.utils.constants.MediaConstants.shareMedia
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.AlertDialog as AlertDialog1

@Composable
fun MediaPreviewScreen(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    mediaTypeName: String,
    mediaList: List<MediaModel>,
    onBackClick: () -> Unit,
    viewModel: MediaPreviewViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    val isLoading by viewModel.isLoading
    val isSuccessful by viewModel.isSuccessful
    val errorMessage by viewModel.errorMessage

    val showErrorDialog = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }

    LaunchedEffect(errorMessage, isSuccessful) {
        if (errorMessage.isNotEmpty()) {
            showErrorDialog.value = true
        }
        if (isSuccessful) {
            showSuccessDialog.value = true
        }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
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
                onDownloadClick = { mediaModel ->
                    viewModel.saveMedia(context, mediaModel)
                },
                onDeleteClick = { mediaModel ->
                    viewModel.deleteMedia(context, mediaModel)
                },
                onShareClick = { mediaModel ->
                    context.shareMedia(mediaModel)
                },
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (showErrorDialog.value) {
            AlertDialogMedia(
                showDialog = showSuccessDialog,
                title = "Error",
                content = "Download Error! (${errorMessage})",
                buttonTitle = "Close"
            )
        }

        if (showSuccessDialog.value) {
            AlertDialogMedia(
                showDialog = showSuccessDialog,
                title = "Success",
                content = "Download Successful!",
                buttonTitle = "OK"
            )
        }
    }
}

@Composable
private fun AlertDialogMedia(
    showDialog: MutableState<Boolean>,
    title: String,
    content: String,
    buttonTitle: String,
) {
    AlertDialog1(
        onDismissRequest = {
            showDialog.value = false
        },
        title = {
            Text(text = title, color = MaterialTheme.colorScheme.primary)
        },
        text = {
            Text(text = content, color = MaterialTheme.colorScheme.onPrimary)
        },
        confirmButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = buttonTitle, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        textContentColor = MaterialTheme.colorScheme.onPrimary
    )
}