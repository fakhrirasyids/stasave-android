package com.fakhrirasyids.stasave.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fakhrirasyids.stasave.core.data.local.room.entity.SavedMediaEntity
import com.fakhrirasyids.stasave.core.utils.enums.MediaType
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SavedMediaDao {
    @Transaction
    suspend fun insertMediaIfNotExists(mediaEntity: SavedMediaEntity) {
        deleteMediaByName(mediaEntity.fileName)
        insertMedia(mediaEntity)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedia(mediaEntity: SavedMediaEntity)

    @Query("DELETE FROM saved_media WHERE fileName =:fileName")
    suspend fun deleteMediaByName(fileName: String)

    @Query("DELETE FROM saved_media WHERE uri =:uri")
    suspend fun deleteMedia(uri: String)

    @Query("SELECT * FROM saved_media WHERE fileType = :fileType")
    fun getAllImageMedia(fileType: String = MediaType.IMAGE.name.lowercase())
            : Flow<List<SavedMediaEntity>>

    @Query("SELECT * FROM saved_media WHERE fileType = :fileType")
    fun getAllVideoMedia(fileType: String = MediaType.VIDEO.name.lowercase())
            : Flow<List<SavedMediaEntity>>
}