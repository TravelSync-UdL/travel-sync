package com.app.travelsync.domain.repository

import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.domain.model.Hotel


interface HotelRepository {

    suspend fun list(groupId: String): List<Hotel>

    suspend fun searchHotels(groupId: String, startDate: String, endDate: String, city: String): List<Hotel>

    suspend fun reserveRoom(groupId: String, hotelId: String ,roomId: String, startDate: String, endDate: String, tripId: Int): ReservationDto

    suspend fun getLocalReservations(): List<ReservationEntity>

    suspend fun getReservationById(resId: Int): ReservationEntity?

    suspend fun getRoomImageUrl(reservationId: String): String?


}
