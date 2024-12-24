package com.fakhrirasyids.stasave.core.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fakhrirasyids.stasave.core.data.local.room.entity.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWhatsappMedias(story: List<MediaEntity>)

    @Query("SELECT * FROM media ORDER BY id DESC")
    fun getWhatsappMediasPaging(): Flow<List<MediaEntity>>

    @Query("DELETE FROM media")
    suspend fun deleteAllMedias()
}