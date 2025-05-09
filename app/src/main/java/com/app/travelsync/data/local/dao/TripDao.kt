package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.app.travelsync.data.local.TripWithImages
import com.app.travelsync.data.local.entity.ImageEntity
import com.app.travelsync.data.local.entity.TripEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM trip WHERE title = :tripName LIMIT 1")
    suspend fun checkTripName(tripName: String): TripEntity?

    @Query("SELECT * FROM trip WHERE ownerLogin = :login")
    suspend fun getTripsForUser(login: String): List<TripEntity>

    @Query("SELECT * FROM trip WHERE id = :tripId")
    suspend fun getTripId(tripId: Int): TripEntity?

    @Transaction
    @Query("SELECT * FROM trip")
    fun getTripsWithImages(): Flow<List<TripWithImages>>

    @Insert
    suspend fun insertImage(image: ImageEntity)

    /* GalleryDao */
    @Query("DELETE FROM image WHERE uri = :uri")
    suspend fun deleteImageByUri(uri: String)

}