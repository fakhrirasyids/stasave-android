package com.fakhrirasyids.stasave.core.domain.repository

import android.content.Context
import com.fakhrirasyids.stasave.core.utils.helper.Result
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import kotlinx.coroutines.flow.Flow

internal interface StasaveRepository {
    // Preferences Utilities
    suspend fun getWhatsappUri(): Flow<String>
    suspend fun saveWhatsappUri(whatsappUri: String)
    suspend fun clearPreferences()

    // Whatsapp Media Utilities
    fun getWhatsappMedias(context: Context): Flow<Result<List<MediaModel>>>

    // Saved Media Utilities
    suspend fun insertMedia(context: Context, mediaModel: MediaModel): Flow<Result<Boolean>>
    suspend fun deleteMedia(context: Context, mediaModel: MediaModel): Flow<Result<Boolean>>
    fun getAllImageMedia(): Flow<Result<List<MediaModel>>>
    fun getAllVideoMedia(): Flow<Result<List<MediaModel>>>
}