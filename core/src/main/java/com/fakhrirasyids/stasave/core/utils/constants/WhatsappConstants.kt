package com.fakhrirasyids.stasave.core.utils.constants

import android.net.Uri
import android.os.Build

object WhatsappConstants {
    const val WHATSAPP_PACKAGE_NAME = "com.whatsapp"

    const val MEDIA_TYPE_IMAGES = "com.whatsapp.images"
    const val MEDIA_TYPE_VIDEOS = "com.whatsapp.videos"

    private val WHATSAPP_PACKAGE_PATH_URI_ANDROID =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%2FMedia%2F.Statuses")
    private val WHATSAPP_PACKAGE_PATH_URI_ANDROID_11 =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")

    fun getWhatsappUri(): Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WHATSAPP_PACKAGE_PATH_URI_ANDROID_11
    } else {
        WHATSAPP_PACKAGE_PATH_URI_ANDROID
    }
}