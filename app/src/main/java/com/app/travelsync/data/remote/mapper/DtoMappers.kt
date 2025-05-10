package com.app.travelsync.data.remote.mapper

import com.app.travelsync.data.remote.dto.HotelDto
import com.app.travelsync.data.remote.dto.ReservationDto
import com.app.travelsync.data.remote.dto.ReserveRequestDto
import com.app.travelsync.data.remote.dto.RoomDto
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Reservation
import com.app.travelsync.domain.model.ReserveRequest
import com.app.travelsync.domain.model.Room

fun HotelDto.toDomain(): Hotel = Hotel(
    id        = id,
    name      = name,
    address   = address,
    rating    = rating,
    imageUrl  = image_url,
    rooms     = rooms
        ?.map { it.toDomain() }      // si no es null lo mapea
        ?: emptyList()               // si es null lista vacía
)

fun RoomDto.toDomain(): Room = Room(
    id       = id,
    roomType = room_type,
    price    = price,
    images   = images ?: emptyList()
)

fun ReservationDto.toDomain(): Reservation = Reservation(
    id         = id,
    hotelId    = hotel_id,
    roomId     = room_id,
    startDate  = start_date,
    endDate    = end_date,
    guestName  = guest_name,
    guestEmail = guest_email,
    hotel = hotel.toDomain(),   // HotelDto → Hotel
    room  = room.toDomain()     // RoomDto  → Room
)

fun ReserveRequest.toDto(): ReserveRequestDto = ReserveRequestDto(
    hotel_id = hotelId,
    room_id = roomId,
    start_date = startDate,
    end_date = endDate,
    guest_name = guestName,
    guest_email = guestEmail
)