package com.app.travelsync.data.repository

import android.util.Log
import com.app.travelsync.data.local.dao.ReservationDao
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.domain.model.Reservation
import com.app.travelsync.domain.repository.ReservationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepositoryImpl @Inject constructor(
    private val reservationDao: ReservationDao
): ReservationRepository{

    override suspend fun insertReservation(reservation: ReservationEntity): Long {
        return try {
            Log.d("Database - Reservation", "Abans d'inserir: ${reservation.roomType}")
            val id = reservationDao.insertReservation(reservation)
            Log.d("Database - Reservation", "Després d'inserir reserva amb ID: $id")
            id
        } catch (e: Exception) {
            Log.e("Database - Reservation", "Error en insertar reserva: ${e.localizedMessage}", e)
            0L
        }
    }

    override suspend fun getReservation(tripId: Int): ReservationEntity?{
        return reservationDao.getReservation(tripId)
    }

    override suspend fun deleteReservation(reservationId: String) {
        Log.d("Database - Reservation", "Abans d'eliminar: ${reservationId}")
        reservationDao.deleteReservation(reservationId)
        Log.d("Database - Reservation", "Després d'eliminar")
    }

}