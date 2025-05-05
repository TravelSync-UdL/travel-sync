package com.app.travelsync.data.repository

import com.app.travelsync.data.remote.api.HotelApiService
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.repository.HotelRepository
import com.app.travelsync.data.remote.mapper.toDomain
import com.app.travelsync.data.remote.mapper.toDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelRepositoryImpl @Inject constructor(
    private val api: HotelApiService
) : HotelRepository {

    private val gid = "G12"

    override suspend fun list(groupId: String): List<Hotel> =
        api.getHotels(gid).map { it.toDomain() }
}