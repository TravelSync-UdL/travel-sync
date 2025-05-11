package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.local.entity.TripEntity
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.domain.model.Reservation

@Dao
interface ReservationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservation(reservation: ReservationEntity): Long

    @Query("DELETE FROM reservations WHERE reservationId = :reservationId")
    suspend fun deleteReservation(reservationId: String)

    @Query("SELECT * FROM reservations WHERE tripId = :tripId")
    suspend fun getReservation(tripId: Int): ReservationEntity?
}