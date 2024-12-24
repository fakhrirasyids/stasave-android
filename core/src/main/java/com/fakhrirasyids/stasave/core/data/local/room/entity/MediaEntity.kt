package com.fakhrirasyids.stasave.core.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fakhrirasyids.stasave.core.utils.enums.MediaType

@Entity(tableName = "media")
internal data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uri: String,
    val fileName: String,
    val fileType: String = MediaType.IMAGE.name.lowercase(),
)