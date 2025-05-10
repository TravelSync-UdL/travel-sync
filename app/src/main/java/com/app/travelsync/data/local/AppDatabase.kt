package com.app.travelsync.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.ReservationDao
import com.app.travelsync.data.local.dao.SessionLogDao
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.data.local.dao.UserDao
import com.app.travelsync.data.local.entity.ImageEntity
import com.app.travelsync.data.local.entity.ItineraryEntity
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.local.entity.SessionLogEntity
import com.app.travelsync.data.local.entity.TripEntity
import com.app.travelsync.data.local.entity.UserEntity

@Database(
    entities = [ItineraryEntity::class, ReservationEntity::class, TripEntity::class, UserEntity::class, SessionLogEntity::class, ImageEntity::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun itineraryDao(): ItineraryDao
    abstract fun userDao(): UserDao
    abstract fun sessionLogDao(): SessionLogDao
    abstract fun reservationDao(): ReservationDao


}