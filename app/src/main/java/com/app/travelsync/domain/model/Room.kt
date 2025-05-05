package com.app.travelsync.domain.model

data class Room(
    val id: String,
    val room_type: String,
    val price: Int,
    val images: List<String>
)