package com.fakhrirasyids.stasave.platform.ui.components

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fakhrirasyids.stasave.platform.utils.constants.PermissionConstants

@Composable
fun ErrorContent(
    errorMessage: String,
    documentTreeLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    if (errorMessage == stringResource(id = com.fakhrirasyids.stasave.common.R.string.invalid_whatsapp_directory)) {
        PermissionComposable(
            title = stringResource(id = com.fakhrirasyids.stasave.common.R.string.screen_whatsapp_directory),
            onRequestPermissionClick = {
                PermissionConstants.getWhatsappFolderPermissions(documentTreeLauncher)
            }
        )
    } else {
        Text(
            modifier = Modifier.padding(16.dp),
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = Color.Red
        )
    }
}