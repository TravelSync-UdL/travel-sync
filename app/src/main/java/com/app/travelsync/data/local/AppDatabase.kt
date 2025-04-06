package com.app.travelsync.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.data.local.entity.ItineraryEntity
import com.app.travelsync.data.local.entity.TripEntity

@Database(
    entities = [ItineraryEntity::class, TripEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryDao(): ItineraryDao
}