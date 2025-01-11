package com.fakhrirasyids.stasave.platform.ui.components

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionWrapper(
    isWhatsappSavedUriPermissionGranted: String,
    documentTreeWhatsappLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    content: @Composable () -> Unit
) {
    val isStoragePermissionGranted = rememberSaveable {
        mutableStateOf(false)
    }
    val storagePermissionState = rememberMultiplePermissionsState(PermissionConstants.STORAGE_PERMISSIONS)

    isStoragePermissionGranted.value = PermissionConstants.checkPermissionsForApiBefore32(storagePermissionState)

    if (!isStoragePermissionGranted.value) {
        PermissionComposable(
            title = stringResource(id = com.fakhrirasyids.stasave.common.R.string.home_permission_not_setted),
            onRequestPermissionClick = {
                storagePermissionState.launchMultiplePermissionRequest()
            }
        )
    } else if (isWhatsappSavedUriPermissionGranted.isEmpty()) {
        PermissionComposable(
            title = stringResource(id = com.fakhrirasyids.stasave.common.R.string.saved_media_permission_not_setted),
            onRequestPermissionClick = {
                PermissionConstants.getWhatsappFolderPermissions(documentTreeWhatsappLauncher)
            }
        )
    } else {
        content()
    }
}