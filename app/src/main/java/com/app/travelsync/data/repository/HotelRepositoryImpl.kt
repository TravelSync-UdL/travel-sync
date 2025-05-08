package com.app.travelsync.data.repository

import android.util.Log
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.data.local.dao.ReservationDao
import com.app.travelsync.data.local.dao.UserDao
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.remote.api.HotelApiService
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.repository.HotelRepository
import com.app.travelsync.data.remote.mapper.toDomain
import com.app.travelsync.data.remote.mapper.toDto
import com.app.travelsync.domain.model.Reservation
import java.security.PrivateKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepositoryImpl @Inject constructor(
    private val api: HotelApiService,
    private val reservationDao: ReservationDao,
    private val sharedPrefsManager: SharedPrefsManager
) : HotelRepository {

    private val gid = "G12"

    override suspend fun list(groupId: String): List<Hotel> =
        api.getHotels(gid).map { it.toDomain() }


    override suspend fun searchHotels(groupId: String, city: String, startDate: String, endDate: String): List<Hotel> {
        val response = api.getAvailability(groupId, city, startDate, endDate)
        // Assuming response is an object with the key 'available_hotels'
        return response.available_hotels.map { it.toDomain() }
    }


    override suspend fun reserveRoom(groupId: String, hotelId: String, roomId: String, startDate: String, endDate: String, tripId: Int): ReservationDto {

        val reservation = Reservation(
            hotel_id = hotelId,
            room_id = roomId,
            start_date = startDate,
            end_date = endDate,
            guest_name = sharedPrefsManager.userUsername ?: "",
            guest_email = sharedPrefsManager.userEmail ?: ""
        )
        val response = api.reserveRoom(groupId, reservation)

        val hotel = api.getHotels(groupId).find { it.id == hotelId }
        val room = hotel?.rooms?.find { it.id == roomId }

        val reservation_id = response.reservation.id

        reservationDao.insertReservation(
            ReservationEntity(
                reservationId = reservation_id,
                hotelName = hotel?.name ?: "Unknown Hotel",
                roomType = room?.room_type ?: "Unknown Room",
                price = room?.price ?: 0,
                startDate = startDate,
                endDate = endDate,
                userEmail = sharedPrefsManager.userEmail ?: "",
                tripId = tripId
            )
        )
        Log.d("Reserva", "Reserva afegida a la base de dades: ${reservation_id}")
        return response
    }


    override suspend fun getLocalReservations(): List<ReservationEntity> {
        return reservationDao.getAllReservations()
    }

    override suspend fun getReservationById(resId: Int): ReservationEntity? {
        return reservationDao.getReservationById(resId)
    }

    override suspend fun getRoomImageUrl(reservationId: String): String? {
        return try {
            val response = api.getReservationFull(reservationId)
            Log.d("IMATGE", "Resposta de l'API: $response")
            response.room.images.firstOrNull() // Retorna la primera imatge
        } catch (e: Exception) {
            Log.e("IMATGE", "Error obtenint la imatge: ${e.localizedMessage}")
            Log.d("IMATGE", "ID de la reserva: $reservationId")

            null
        }
    }




}