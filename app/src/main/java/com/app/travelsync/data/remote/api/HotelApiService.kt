package com.app.travelsync.data.remote.api

import com.app.travelsync.data.remote.dto.HotelDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelApiService{

    @GET("/hotels/{group_id}/hotels")
    suspend fun getHotels(@Path("group_id") groupId: String): List<HotelDto>
}

data class ResponseBodyDto(
    val ok: Boolean,
    val message: String,
    val hotel: HotelDto?
)