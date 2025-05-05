package com.app.travelsync.data.remote.mapper

import com.app.travelsync.data.remote.dto.HotelDto
import com.app.travelsync.domain.model.Hotel

//mapper
fun HotelDto.toDomain() = Hotel(id, name, address, rating, image_url, rooms)
fun Hotel.toDto()       = HotelDto(id, name, address, rating, image_url, rooms)