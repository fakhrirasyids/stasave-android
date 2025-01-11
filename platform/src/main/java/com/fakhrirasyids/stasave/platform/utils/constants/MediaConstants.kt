package com.fakhrirasyids.stasave.platform.utils.constants

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.utils.enums.MediaType

object MediaConstants {
    fun Context.shareMedia(mediaModel: MediaModel) {
        try {
            val uri = Uri.parse(mediaModel.uri)
            val mimeType = if (mediaModel.fileType == MediaType.VIDEO.name.lowercase()) {
                "video/*"
            } else {
                "image/*"
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            this.startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}