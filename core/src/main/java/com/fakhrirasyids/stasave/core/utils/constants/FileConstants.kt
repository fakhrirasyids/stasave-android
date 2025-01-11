package com.fakhrirasyids.stasave.core.utils.constants

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import java.io.File

internal object FileConstants {
    fun Context.saveMedia(model: MediaModel): String? {
        val mediaUri = isMediaExist(model, true)
        if (mediaUri != null) return mediaUri

        return saveMediaOnAndroidQAndAbove(model)
    }

    fun Context.deleteMedia(fileName: String): Boolean {
        return deleteAllMedia(fileName)
    }

    private fun Context.saveMediaOnAndroidQAndAbove(model: MediaModel): String? {
        val mimeType = "${model.fileType}/${getFileExtension(model.fileName)}"
        val inputStream = contentResolver.openInputStream(model.uri.toUri()) ?: return null

        val existingUri = getExistingMediaUri(model.fileName, mimeType)
        if (existingUri != null) {
            return existingUri.toString()
        }

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.DISPLAY_NAME, model.fileName)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "${Environment.DIRECTORY_DOCUMENTS}/${
                    ContextCompat.getString(
                        this@saveMediaOnAndroidQAndAbove,
                        com.fakhrirasyids.stasave.common.R.string.app_name
                    )
                }"
            )
        }

        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        return uri?.let { uriPath ->
            contentResolver.openOutputStream(uriPath)?.use { outputStream ->
                inputStream.copyTo(outputStream)
                uriPath.toString()
            }
        }
    }

    private fun Context.deleteAllMedia(fileName: String): Boolean {
// Define the app's folder in the Documents directory
        val appFolder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            ContextCompat.getString(this, com.fakhrirasyids.stasave.common.R.string.app_name)
        )

        if (!appFolder.exists() || !appFolder.isDirectory) return false // App folder doesn't exist

        // Extract the base name and extension of the file
        val baseName = fileName.substringBeforeLast(".")
        val extension = fileName.substringAfterLast(".", "")

        // Regex to match files that are NOT suffix (1)
        val regexToDelete =
            Regex("^${Regex.escape(baseName)}( \\((?!1\\)\\d+)\\))?\\.${Regex.escape(extension)}\$")

        // Regex to match files with suffix (1)
        val regexToKeep = Regex("^${Regex.escape(baseName)} \\(1\\)\\.${Regex.escape(extension)}\$")

        var allDeleted = true

        appFolder.listFiles()?.forEach { file ->
            if (file.name.matches(regexToKeep)) {
                return@forEach
            }

            if (file.name.matches(regexToDelete)) {
                if (!file.delete()) {
                    allDeleted = false
                }
            }
        }

        return allDeleted
    }


    fun getFileExtension(fileName: String) =
        fileName.substringAfterLast('.', "")

    private fun Context.getExistingMediaUri(fileName: String, mimeType: String): Uri? {
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE
        )
        val selection =
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ? AND ${MediaStore.MediaColumns.MIME_TYPE} = ?"
        val selectionArgs = arrayOf(fileName, mimeType)

        contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                return ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), id)
            }
        }
        return null
    }

    fun Context.isMediaExist(mediaModel: MediaModel, isDownloading: Boolean = false): String? {
        val targetFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "${
                ContextCompat.getString(
                    this,
                    com.fakhrirasyids.stasave.common.R.string.app_name
                )
            }/${mediaModel.fileName}"
        )

        return if (targetFile.exists()) {
            if (isDownloading) {
//                deleteAllMedia(mediaModel.fileName)
                saveMediaOnAndroidQAndAbove(mediaModel)
            } else {
                ""
            }
        } else {
            return null
        }
    }
}