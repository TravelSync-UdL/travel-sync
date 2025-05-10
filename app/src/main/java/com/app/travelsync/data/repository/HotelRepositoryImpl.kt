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
import com.app.travelsync.domain.model.ReserveRequest
import java.security.PrivateKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepositoryImpl @Inject constructor(
    private val api: HotelApiService
) : HotelRepository {

    /* ---------- Hotels ---------- */
    override suspend fun getHotels(groupId: String): List<Hotel> =
        api.getHotels(groupId).map { it.toDomain() }

    /* ---------- Availability ---------- */
    override suspend fun getAvailability(
        groupId: String,
        start: String,
        end: String,
        hotelId: String?,
        city: String?
    ): List<Hotel> =
        api.getAvailability(groupId, start, end, hotelId, city)
            .available_hotels
            .map { it.toDomain() }

    /* ---------- Reserve & Cancel (within group) ---------- */
    override suspend fun reserve(groupId: String, request: ReserveRequest): Reservation =
        api.reserveRoom(groupId, request.toDto()).reservation.toDomain()

    override suspend fun cancel(groupId: String, request: ReserveRequest): String =
        api.cancelReservation(groupId, request.toDto()).message

    /* ---------- Reservations for a group ---------- */
    override suspend fun getGroupReservations(
        groupId: String,
        guestEmail: String?
    ): List<Reservation> =
        api.getGroupReservations(groupId, guestEmail).reservations.map { it.toDomain() }

    /* ---------- All reservations (admin) ---------- */
    override suspend fun getAllReservations(): Map<String, List<Reservation>> =
        api.getAllReservations()
            .groups
            .mapValues { entry -> entry.value.map { it.toDomain() } }

    /* ---------- Single-ID operations ---------- */
    override suspend fun getReservationById(resId: String): Reservation =
        api.getReservationById(resId).toDomain()

    override suspend fun cancelById(resId: String): Reservation =
        api.deleteReservationById(resId).toDomain()
}