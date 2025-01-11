package com.fakhrirasyids.stasave.platform.utils.constants

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.DocumentsContract
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.fakhrirasyids.stasave.core.utils.constants.WhatsappConstants
import com.fakhrirasyids.stasave.platform.ui.screens.main.home.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
object PermissionConstants {
    fun checkPermissionsForApiBefore32(
        storagePermissionState: MultiplePermissionsState
    ) = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        storagePermissionState.permissions.all { it.status.isGranted }
    } else {
        true
    }

    fun getWhatsappFolderPermissions(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, WhatsappConstants.getWhatsappUri())
            putExtra("android.content.extra.SHOW_ADVANCED", true)
        }
        launcher.launch(intent)
    }

    fun handleWhatsappFolderSelection(
        result: ActivityResult,
        context: Context,
        viewModel: HomeViewModel
    ) {
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.saveWhatsappSavedFolderUri(uri.toString())
            } else {
                Log.e("FTEST", "Failed to select folder")
            }
        } else {
            Log.e("FTEST", "Failed to select folder")
        }
    }

    val STORAGE_PERMISSIONS = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
}