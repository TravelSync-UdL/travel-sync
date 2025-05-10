package com.app.travelsync.data.local.mapper

import com.app.travelsync.data.local.entity.ItineraryEntity
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.data.local.entity.TripEntity
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Reservation
import com.app.travelsync.domain.model.Room
import com.app.travelsync.domain.model.Trip

// De dominio a entidad
fun Trip.toEntity(): TripEntity =
    TripEntity(id = tripId, title = title, destination = destination, startDate = startDate, endDate = endDate, ownerLogin = ownerLogin, images = images.toString())

fun Itinerary.toEntity(): ItineraryEntity =
    ItineraryEntity(id = itineraryId, trip_Id = trip_Id, title = title, date = date, time = time, location = location, notes = notes)

// De entidad a dominio
fun TripEntity.toDomain(itinerary: List<Itinerary>): Trip =
    Trip(tripId = id, title = title, destination = destination, startDate = startDate, endDate = endDate, ownerLogin = ownerLogin ,itinerary = itinerary)

fun ItineraryEntity.toDomain(): Itinerary =
    Itinerary(itineraryId = id, trip_Id = trip_Id, title = title, date = date, time = time, location = location, notes = notes)