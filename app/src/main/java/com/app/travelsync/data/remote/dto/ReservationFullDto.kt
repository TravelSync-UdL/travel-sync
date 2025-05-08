package com.app.travelsync.data.remote.dto

data class ReservationFullDto(
    val room: RoomDto
)

data class RoomDto(
    val id: String,
    val room_type: String,
    val price: Int,
    val images: List<String>
)
