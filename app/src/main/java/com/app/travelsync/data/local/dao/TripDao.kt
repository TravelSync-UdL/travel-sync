package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.travelsync.data.local.entity.TripEntity

@Dao
interface TripDao {
    @Query("SELECT * FROM trip")
    suspend fun getTrip(): List<TripEntity>

    @Insert
    suspend fun addTrip(trip: TripEntity): Long

    @Query("DELETE FROM trip WHERE id = :tripId")
    suspend fun deleteTrip(tripId: Int)

    @Update
    suspend fun editTrip(trip: TripEntity)
}