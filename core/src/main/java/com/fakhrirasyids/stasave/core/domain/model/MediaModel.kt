package com.fakhrirasyids.stasave.core.domain.model

import android.os.Parcelable
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaModel(
    val id: Int = 0,
    val uri: String,
    val fileName: String,
    val fileType: String = MediaType.IMAGE.name.lowercase(),
) : Parcelable