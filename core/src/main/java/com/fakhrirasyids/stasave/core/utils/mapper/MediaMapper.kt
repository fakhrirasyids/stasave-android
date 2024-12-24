package com.fakhrirasyids.stasave.core.utils.mapper

import com.fakhrirasyids.stasave.core.data.local.room.entity.MediaEntity
import com.fakhrirasyids.stasave.core.data.local.room.entity.SavedMediaEntity
import com.fakhrirasyids.stasave.core.domain.model.MediaModel

internal object MediaMapper {
    fun MediaModel.toMediaEntity() =
        MediaEntity(
            uri = uri,
            fileName = fileName,
            fileType = fileType,
        )

    fun MediaModel.toSavedMediaEntity() =
        SavedMediaEntity(
            uri = uri,
            fileName = fileName,
            fileType = fileType,
        )

    fun MediaEntity.toMediaModel() =
        MediaModel(
            uri = uri,
            fileName = fileName,
            fileType = fileType,
        )

    fun SavedMediaEntity.toMediaModel() =
        MediaModel(
            uri = uri,
            fileName = fileName,
            fileType = fileType,
        )
}