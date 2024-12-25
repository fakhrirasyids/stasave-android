package com.fakhrirasyids.stasave.platform.ui.screens.main.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fakhrirasyids.stasave.common.theme.StasaveTheme
import com.fakhrirasyids.stasave.platform.ui.components.PermissionWrapper
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.ui.screens.main.widgets.MediaPagerWidget
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val whatsappUri by viewModel.whatsappUri
    val isLoading by viewModel.isLoading
    val whatsappVideoMedias by viewModel.whatsappVideoMedias
    val whatsappImageMedias by viewModel.whatsappImageMedias
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(whatsappUri) {
        if (whatsappUri.isNotEmpty()) {
            viewModel.getAllWhatsappImages(context)
        }
    }

    val openDocumentTreeLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            PermissionConstants.handleFolderSelection(result, context, viewModel)
        }
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBanner(
            title = MainScreen.Home.name,
            onRefreshClick = {
                if (whatsappUri.isNotEmpty()) {
                    viewModel.getAllWhatsappImages(context)
                }
            }
        )

        PermissionWrapper(
            isWhatsappSavedUriPermissionGranted = whatsappUri,
            documentTreeLauncher = openDocumentTreeLauncher
        ) {
            MediaPagerWidget(
                documentTreeLauncher = openDocumentTreeLauncher,
                whatsappImageMedias = whatsappImageMedias,
                whatsappVideoMedias = whatsappVideoMedias,
                isLoading = isLoading,
                isErrorMessage = errorMessage
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    StasaveTheme {
        HomeScreen()
    }
}