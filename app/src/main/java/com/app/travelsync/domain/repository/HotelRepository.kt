package com.app.travelsync.domain.repository

import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Reservation
import com.app.travelsync.domain.model.ReserveRequest


interface HotelRepository {


    suspend fun getHotels(groupId: String): List<Hotel>
    suspend fun getAvailability(
        groupId: String,
        start: String,
        end: String,
        hotelId: String? = null,
        city: String? = null
    ): List<Hotel>


    suspend fun reserve(groupId: String, request: ReserveRequest): Reservation
    suspend fun cancel(groupId: String, request: ReserveRequest): String


    suspend fun getGroupReservations(
        groupId: String,
        guestEmail: String? = null
    ): List<Reservation>

    suspend fun getAllReservations(): Map<String, List<Reservation>>


    suspend fun getReservationById(resId: String): Reservation
    suspend fun cancelById(resId: String): Reservation

    suspend fun getRoomImageUrl(resId: String): String
}
