package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.travelsync.data.local.entity.ItineraryEntity

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itinerary WHERE trip_Id = :tripId")
    suspend fun getItinerary(tripId: Int): List<ItineraryEntity>

    @Insert
    suspend fun addItinerary(itinerary: ItineraryEntity): Long

    @Query("DELETE FROM itinerary WHERE id = :itineraryId")
    suspend fun deleteItinerary(itineraryId: Int)

    @Update
    suspend fun editItinerary(itinerary: ItineraryEntity)
}
