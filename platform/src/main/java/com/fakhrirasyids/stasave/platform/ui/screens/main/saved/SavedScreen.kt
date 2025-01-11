package com.fakhrirasyids.stasave.platform.ui.screens.main.saved

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.platform.ui.components.PermissionWrapper
import com.fakhrirasyids.stasave.platform.ui.components.TopBanner
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeViewModel
import com.fakhrirasyids.stasave.platform.ui.screens.main.widgets.MediaPagerWidget
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants
import com.fakhrirasyids.stasave.platform.utils.enums.MainScreen

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    savedViewModel: SavedViewModel,
    onNavigateToMediaPreview: ((Int, String, List<MediaModel>) -> Unit)? = null,
) {
    val context = LocalContext.current

    val whatsappUri by homeViewModel.whatsappUri
    val isLoading by savedViewModel.isLoading
    val savedVideoMedias by savedViewModel.savedVideoMedias
    val savedImageMedias by savedViewModel.savedImageMedias
    val errorMessage by savedViewModel.errorMessage

    val openDocumentTreeWhatsappLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            PermissionConstants.handleWhatsappFolderSelection(result, context, homeViewModel)
        }
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBanner(
            title = MainScreen.Saved.name,
            onRefreshClick = {
                if (whatsappUri.isNotEmpty()) {
                    savedViewModel.getAllSavedImages(context)
                    savedViewModel.getAllSavedVideos(context)
                }
            }
        )

        PermissionWrapper(
            isWhatsappSavedUriPermissionGranted = whatsappUri,
            documentTreeWhatsappLauncher = openDocumentTreeWhatsappLauncher,
        ) {
            MediaPagerWidget(
                documentTreeLauncher = openDocumentTreeWhatsappLauncher,
                whatsappImageMedias = savedImageMedias,
                whatsappVideoMedias = savedVideoMedias,
                isLoading = isLoading,
                isErrorMessage = errorMessage,
                isFromSaved = true,
                onNavigateToMediaPreview = { index, mediaType, mediaList ->
                    onNavigateToMediaPreview?.invoke(index, mediaType, mediaList)
                }
            )
        }
    }
}