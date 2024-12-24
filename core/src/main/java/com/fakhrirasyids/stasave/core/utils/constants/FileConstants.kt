package com.fakhrirasyids.stasave.core.utils.constants

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

internal object FileConstants {
    fun Context.saveMedia(model: MediaModel): Boolean {
        if (isMediaExist(model.fileName)) return true

        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            saveMediaBeforeQ(model.uri.toUri())
        } else {
            saveMediaOnAndroidQAndAbove(model)
        }
    }

    fun Context.deleteMedia(fileName: String): Boolean {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            deleteMediaBeforeQ(fileName)
        } else {
            deleteMediaOnAndroidQAndAbove(fileName)
        }
    }

    private fun Context.saveMediaOnAndroidQAndAbove(model: MediaModel): Boolean {
        val mimeType = "${model.fileType}/${getFileExtension(model.fileName)}"
        val inputStream = contentResolver.openInputStream(model.uri.toUri()) ?: return false

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.DISPLAY_NAME, model.fileName)
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "${Environment.DIRECTORY_DOCUMENTS}/stasave"
            )
        }

        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        return uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                inputStream.copyTo(outputStream)
                true
            } ?: false
        } ?: false
    }

    private fun Context.saveMediaBeforeQ(uri: Uri): Boolean {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return false
            val fileName = uri.lastPathSegment ?: return false
            val destinationDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "stasave"
            )

            if (!destinationDir.exists()) destinationDir.mkdirs()

            val outputFile = File(destinationDir, fileName)
            FileOutputStream(outputFile).use { outputStream ->
                inputStream.copyTo(outputStream)
                true
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun Context.deleteMediaOnAndroidQAndAbove(fileName: String): Boolean {
        val fileUri = getMediaUri(fileName) ?: return false
        return contentResolver.delete(fileUri, null, null) > 0
    }

    private fun deleteMediaBeforeQ(fileName: String): Boolean {
        val targetFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "stasave/$fileName"
        )
        return targetFile.exists() && targetFile.delete()
    }

    private fun Context.getMediaUri(fileName: String): Uri? {
        val projection = arrayOf(MediaStore.MediaColumns._ID)
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(fileName)

        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val id = it.getLong(idColumn)
                return ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), id)
            }
        }
        return null
    }

    fun getFileExtension(fileName: String) =
        fileName.substringAfterLast('.', "")

    private fun isMediaExist(fileName: String): Boolean {
        val targetFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            "stasave/$fileName"
        )
        return targetFile.exists()
    }
}