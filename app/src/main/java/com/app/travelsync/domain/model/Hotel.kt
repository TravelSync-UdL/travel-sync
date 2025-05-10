package com.app.travelsync.domain.model

data class Hotel(
    val id: String,
    val name: String,
    val address: String,
    val rating: Int,
    val imageUrl: String,
    val rooms: List<Room>? = emptyList()
)
