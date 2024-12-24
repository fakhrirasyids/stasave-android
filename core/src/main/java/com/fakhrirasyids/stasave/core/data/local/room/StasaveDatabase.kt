package com.fakhrirasyids.stasave.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fakhrirasyids.stasave.core.data.local.room.dao.MediaDao
import com.fakhrirasyids.stasave.core.data.local.room.dao.SavedMediaDao
import com.fakhrirasyids.stasave.core.data.local.room.entity.MediaEntity
import com.fakhrirasyids.stasave.core.data.local.room.entity.SavedMediaEntity

@Database(
    entities = [MediaEntity::class, SavedMediaEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class StasaveDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun savedMediaDao(): SavedMediaDao
}