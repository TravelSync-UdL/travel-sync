package com.app.travelsync.data.remote.api

import com.app.travelsync.data.remote.dto.HotelAvailabilityResponseDto
import retrofit2.http.Query
import com.app.travelsync.data.remote.dto.HotelDto
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.domain.model.Reservation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HotelApiService{

    @GET("/hotels/{group_id}/hotels")
    suspend fun getHotels(@Path("group_id") groupId: String): List<HotelDto>

    @GET("hotels/{group_id}/availability")
    suspend fun getAvailability(
        @Path("group_id") groupId: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("city") city: String
    ): HotelAvailabilityResponseDto

    @POST("hotels/{group_id}/reserve")
    suspend fun reserveRoom(
        @Path("group_id") groupId: String,
        @Body request: Reservation
    ): ReservationDto

}

data class ResponseBodyDto(
    val ok: Boolean,
    val message: String,
    val hotel: HotelDto?
)