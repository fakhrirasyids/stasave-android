package com.fakhrirasyids.stasave.core.domain.usecase

import android.content.Context
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository
import com.fakhrirasyids.stasave.core.utils.helper.Result
import kotlinx.coroutines.flow.Flow

interface SavedMediaUseCase {
    suspend fun insertMedia(context: Context, mediaModel: MediaModel): Flow<Result<Boolean>>
    suspend fun deleteMedia(context: Context, mediaModel: MediaModel): Flow<Result<Boolean>>
    suspend fun getAllImageMedia(): Flow<Result<List<MediaModel>>>
    suspend fun getAllVideoMedia(): Flow<Result<List<MediaModel>>>
}

internal class SavedMediaInteractor(
    private val stasaveRepository: StasaveRepository
) : SavedMediaUseCase {
    override suspend fun insertMedia(
        context: Context,
        mediaModel: MediaModel
    ) = stasaveRepository.insertMedia(context, mediaModel)

    override suspend fun deleteMedia(
        context: Context,
        mediaModel: MediaModel
    ) = stasaveRepository.deleteMedia(context, mediaModel)

    override suspend fun getAllImageMedia() =
        stasaveRepository.getAllImageMedia()

    override suspend fun getAllVideoMedia() =
        stasaveRepository.getAllVideoMedia()
}