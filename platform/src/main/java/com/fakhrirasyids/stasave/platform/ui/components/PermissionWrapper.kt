package com.fakhrirasyids.stasave.platform.ui.components

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionWrapper(
    modifier: Modifier = Modifier,
    isWhatsappSavedUriPermissionGranted: String,
    documentTreeLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    content: @Composable () -> Unit
) {
    val isStoragePermissionApiBelow32Granted = rememberSaveable { mutableStateOf(false) }
    val storagePermissionApiBelow32State =
        rememberMultiplePermissionsState(PermissionConstants.STORAGE_PERMISSIONS)
    isStoragePermissionApiBelow32Granted.value =
        PermissionConstants.checkPermissionsForApiBefore32(storagePermissionApiBelow32State)

    Column(modifier = modifier) {
        if (isStoragePermissionApiBelow32Granted.value) {
            if (isWhatsappSavedUriPermissionGranted.isNotEmpty()) {
                content()
            } else {
                PermissionComposable(
                    title = stringResource(id = com.fakhrirasyids.stasave.common.R.string.saved_media_permission_not_setted),
                    onRequestPermissionClick = {
                        PermissionConstants.getWhatsappFolderPermissions(documentTreeLauncher)
                    }
                )
            }
        } else {
            PermissionComposable(
                title = stringResource(id = com.fakhrirasyids.stasave.common.R.string.home_permission_not_setted),
                onRequestPermissionClick = {
                    storagePermissionApiBelow32State.launchMultiplePermissionRequest()
                }
            )
        }
    }
}