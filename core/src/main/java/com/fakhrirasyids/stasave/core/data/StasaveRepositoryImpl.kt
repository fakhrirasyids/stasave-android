package com.fakhrirasyids.stasave.core.data

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.fakhrirasyids.stasave.core.data.local.LocalDataSource
import com.fakhrirasyids.stasave.core.data.local.room.entity.MediaEntity
import com.fakhrirasyids.stasave.core.domain.model.MediaModel
import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository
import com.fakhrirasyids.stasave.core.utils.constants.FileConstants
import com.fakhrirasyids.stasave.core.utils.constants.FileConstants.deleteMedia
import com.fakhrirasyids.stasave.core.utils.constants.FileConstants.isMediaExist
import com.fakhrirasyids.stasave.core.utils.constants.FileConstants.saveMedia
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import com.fakhrirasyids.stasave.core.utils.helper.Result
import com.fakhrirasyids.stasave.core.utils.mapper.MediaMapper.toMediaModel
import com.fakhrirasyids.stasave.core.utils.mapper.MediaMapper.toSavedMediaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking

internal class StasaveRepositoryImpl(
    private val localDataSource: LocalDataSource
) : StasaveRepository {
    override fun getWhatsappMedias(context: Context): Flow<Result<List<MediaModel>>> =
        flow {
            emit(Result.Loading)
            try {
                val whatsappUri = runBlocking { localDataSource.getWhatsappUri().first() }
                if (whatsappUri.isEmpty()) {
                    throw IllegalArgumentException("WhatsApp URI is not set in preferences.")
                }

                val whatsappUriParsed = whatsappUri.toUri()
                context.contentResolver.takePersistableUriPermission(
                    whatsappUriParsed,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val whatsappDirectory = DocumentFile.fromTreeUri(context, whatsappUriParsed)
                if (whatsappDirectory == null || !whatsappDirectory.exists() || !whatsappDirectory.isDirectory) {
                    throw IllegalArgumentException(
                        ContextCompat.getString(
                            context,
                            com.fakhrirasyids.stasave.common.R.string.invalid_whatsapp_directory
                        )
                    )
                }

                runBlocking { localDataSource.deleteAllMedias() }

                val whatsappMediaList = mutableListOf<MediaEntity>()
                whatsappDirectory.listFiles().forEach { file ->
                    if (file.name != ".nomedia" && file.isFile) {
                        val fileType = when {
                            FileConstants.getFileExtension(
                                file.name ?: ""
                            ) == "mp4" -> MediaType.VIDEO.name.lowercase()

                            else -> MediaType.IMAGE.name.lowercase()
                        }

                        val mediaEntity = MediaEntity(
                            uri = file.uri.toString(),
                            fileName = file.name ?: "",
                            fileType = fileType
                        )
                        whatsappMediaList.add(mediaEntity)
                    }
                }

                runBlocking { localDataSource.insertWhatsappMedias(whatsappMediaList) }
                val savedWhatsappMediaList = runBlocking {
                    localDataSource.getWhatsappMediasPaging()
                        .map { listMedia -> listMedia.map { it.toMediaModel() } }.first()
                }

                emit(Result.Success(savedWhatsappMediaList))
            } catch (e: IllegalArgumentException) {
                emit(Result.Error(e.message ?: "Unknown Error"))
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Unknown Error"))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getWhatsappUri() =
        localDataSource.getWhatsappUri()

    override suspend fun saveWhatsappUri(whatsappUri: String) {
        localDataSource.saveWhatsappUri(whatsappUri)
    }

    override suspend fun clearPreferences() {
        localDataSource.clearPreferences()
    }

    override suspend fun insertMedia(
        context: Context,
        mediaModel: MediaModel
    ): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        try {
            val isSuccessfullySaved = context.saveMedia(mediaModel)
            if (isSuccessfullySaved) {
                runBlocking { localDataSource.insertMedia(mediaModel.toSavedMediaEntity()) }
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Failed to save media"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteMedia(
        context: Context,
        mediaModel: MediaModel
    ): Flow<Result<Boolean>> = flow {
        emit(Result.Loading)
        try {
            val isSuccessfullyDeleted = context.deleteMedia(mediaModel.fileName)
            if (isSuccessfullyDeleted) {
                runBlocking { localDataSource.deleteMedia(mediaModel.uri) }
                emit(Result.Success(true))
            } else {
                emit(Result.Error("Failed to delete media"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllImageMedia(context: Context): Flow<Result<List<MediaModel>>> = flow {
        emit(Result.Loading)
        try {
            val mediaList = runBlocking {
                localDataSource.getAllImageMedia()
                    .map { it.map { entity -> entity.toMediaModel() } }.first().toMutableList()
            }

            val filteredMediaList = mediaList.filter { mediaModel ->
                val isExist = context.isMediaExist(mediaModel.fileName)
                if (!isExist) {
                    runBlocking { localDataSource.deleteMedia(mediaModel.uri) }
                }
                isExist
            }

            emit(Result.Success(filteredMediaList))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllVideoMedia(context: Context): Flow<Result<List<MediaModel>>> = flow {
        emit(Result.Loading)
        try {
            val mediaList = runBlocking {
                localDataSource.getAllVideoMedia()
                    .map { it.map { entity -> entity.toMediaModel() } }.first().toMutableList()
            }

            val filteredMediaList = mediaList.filter { mediaModel ->
                val isExist = context.isMediaExist(mediaModel.fileName)
                if (!isExist) {
                    runBlocking { localDataSource.deleteMedia(mediaModel.uri) }
                }
                isExist
            }

            emit(Result.Success(filteredMediaList))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown Error"))
        }
    }.flowOn(Dispatchers.IO)
}