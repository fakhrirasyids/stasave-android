package com.fakhrirasyids.stasave.core.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhrirasyids.stasave.core.data.local.room.entity.SavedMediaEntity
import com.fakhrirasyids.stasave.core.utils.enums.MediaType

@Dao
internal interface SavedMediaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedia(mediaEntity: SavedMediaEntity)

    @Query("DELETE FROM saved_media WHERE id =:id")
    suspend fun deleteMedia(id: Int)

    @Query("SELECT * FROM saved_media WHERE fileType = :fileType")
    fun getAllImageMedia(fileType: String = MediaType.IMAGE.name.lowercase())
            : LiveData<List<SavedMediaEntity>>

    @Query("SELECT * FROM saved_media WHERE fileType = :fileType")
    fun getAllVideoMedia(fileType: String = MediaType.VIDEO.name.lowercase())
            : LiveData<List<SavedMediaEntity>>
}