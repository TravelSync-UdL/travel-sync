package com.app.travelsync.data.remote.dto

data class ReservationDto(
    val message: String,
    val nights: Int,
    val reservation: ReservationDetailsDto
)

data class ReservationDetailsDto(
    val id: String,
    val hotel_id: String,
    val room_id: String,
    val start_date: String,
    val end_date: String,
    val guest_name: String,
    val guest_email: String
)
