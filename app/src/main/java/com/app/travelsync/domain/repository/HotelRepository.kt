package com.app.travelsync.domain.repository

import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.domain.model.Hotel
import com.google.android.gms.common.util.Strings

interface HotelRepository {
    suspend fun list(groupId: String): List<Hotel>
    suspend fun searchHotels(groupId: String, startDate: String, endDate: String, city: String): List<Hotel>

    suspend fun reserveRoom(groupId: String, hotelId: String ,roomId: String, startDate: String, endDate: String): ReservationDto

    suspend fun getLocalReservations(): List<ReservationEntity>

}
