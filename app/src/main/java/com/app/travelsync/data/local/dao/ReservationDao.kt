package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.domain.model.Reservation

@Dao
interface ReservationDao {
    @Insert
    suspend fun insertReservation(reservation: ReservationEntity)

    @Query("SELECT * FROM reservation")
    suspend fun getAllReservations(): List<ReservationEntity>

    /*@Query("DELETE FROM reservation WHERE reservationId = :resId")
    suspend fun deleteReservation(resId: String)*/
}