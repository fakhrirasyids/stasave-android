package com.fakhrirasyids.stasave.core.data.local

import androidx.lifecycle.LiveData
import com.fakhrirasyids.stasave.core.data.local.datastore.StasavePreferences
import com.fakhrirasyids.stasave.core.data.local.room.dao.MediaDao
import com.fakhrirasyids.stasave.core.data.local.room.dao.SavedMediaDao
import com.fakhrirasyids.stasave.core.data.local.room.entity.MediaEntity
import com.fakhrirasyids.stasave.core.data.local.room.entity.SavedMediaEntity
import kotlinx.coroutines.flow.Flow

internal interface LocalDataSource {
    // Preferences Utilities
    fun getWhatsappUri(): Flow<String>
    suspend fun saveWhatsappUri(whatsappUri: String)
    suspend fun clearPreferences()

    // Whatsapp Media Utilities
    suspend fun insertWhatsappMedias(mediaList: List<MediaEntity>)
    suspend fun deleteAllMedias()
    fun getWhatsappMediasPaging(): Flow<List<MediaEntity>>

    // Saved Media Utilities
    suspend fun insertMedia(mediaEntity: SavedMediaEntity)
    suspend fun deleteMedia(uri: String)
    fun getAllImageMedia(): Flow<List<SavedMediaEntity>>
    fun getAllVideoMedia(): Flow<List<SavedMediaEntity>>
}

internal class LocalDataSourceImpl(
    private val mediaDao: MediaDao,
    private val savedMediaDao: SavedMediaDao,
    private val preferences: StasavePreferences
) : LocalDataSource {
    override fun getWhatsappUri() =
        preferences.getWhatsappUri()

    override suspend fun saveWhatsappUri(whatsappUri: String) {
        preferences.saveWhatsappUri(whatsappUri)
    }

    override suspend fun clearPreferences() {
        preferences.clearPreferences()
    }

    override suspend fun insertWhatsappMedias(mediaList: List<MediaEntity>) {
        mediaDao.insertWhatsappMedias(mediaList)
    }

    override suspend fun deleteAllMedias() {
        mediaDao.deleteAllMedias()
    }

    override fun getWhatsappMediasPaging() =
        mediaDao.getWhatsappMediasPaging()

    override suspend fun insertMedia(mediaEntity: SavedMediaEntity) {
        savedMediaDao.insertMedia(mediaEntity)
    }

    override suspend fun deleteMedia(uri: String) {
        savedMediaDao.deleteMedia(uri)
    }

    override fun getAllImageMedia() =
        savedMediaDao.getAllImageMedia()

    override fun getAllVideoMedia() =
        savedMediaDao.getAllVideoMedia()
}