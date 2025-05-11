package com.app.travelsync.domain.repository

import com.app.travelsync.data.local.entity.ReservationEntity

interface ReservationRepository {

    suspend fun insertReservation(reservation: ReservationEntity): Long
    suspend fun getReservation(tripId: Int): ReservationEntity?
    suspend fun deleteReservation(reservationId: String)

}