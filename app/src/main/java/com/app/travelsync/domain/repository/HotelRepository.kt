package com.app.travelsync.domain.repository

import com.app.travelsync.domain.model.Hotel

interface HotelRepository {
    suspend fun list(groupId: String): List<Hotel>
    //suspend fun checkAvailability(groupId: String, startDate: String, endDate: String): List<Hotel>
}
