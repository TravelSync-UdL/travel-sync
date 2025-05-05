package com.app.travelsync.data.remote.dto

import com.app.travelsync.domain.model.Room

data class HotelDto(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int?,
    val image_url: String,
    val rooms: List<Room>
)