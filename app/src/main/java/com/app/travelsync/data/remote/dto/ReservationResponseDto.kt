package com.app.travelsync.data.remote.dto

data class ReservationResponseDto(
    val message: String,
    val nights: Int,
    val reservation: ReservationDto        // ya trae hotel + room
)
