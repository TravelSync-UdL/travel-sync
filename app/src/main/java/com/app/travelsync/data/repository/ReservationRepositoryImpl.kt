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

    override suspend fun insertReservation(reservation: ReservationEntity): Long {  // Cambiat a Long per retornar l'ID
        return try {
            Log.d("Database - Reservation", "Abans d'inserir: ${reservation.roomType}")
            val id = reservationDao.insertReservation(reservation)  // El valor retornat de la inserció
            Log.d("Database - Reservation", "Després d'inserir reserva amb ID: $id")
            id  // Retorna l'ID generat
        } catch (e: Exception) {
            Log.e("Database - Reservation", "Error en insertar reserva: ${e.localizedMessage}", e)
            0L  // Retorna 0L en cas d'error
        }
    }

}