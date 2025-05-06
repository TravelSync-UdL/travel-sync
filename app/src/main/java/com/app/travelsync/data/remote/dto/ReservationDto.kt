package com.app.travelsync.data.remote.dto

data class ReservationDto(
    val reservation_id: String,
    val hotel_id: String,
    val room_id: String,
    val start_date: String,
    val end_date: String
)