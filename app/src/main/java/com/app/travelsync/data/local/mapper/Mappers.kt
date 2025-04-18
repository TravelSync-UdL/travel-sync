package com.app.travelsync.data.local.mapper

import com.app.travelsync.data.local.entity.ItineraryEntity
import com.app.travelsync.data.local.entity.TripEntity
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip


fun Trip.toEntity(): TripEntity =
    TripEntity(id = tripId, title = title, destination = destination, startDate = startDate, endDate = endDate)

fun Itinerary.toEntity(): ItineraryEntity =
    ItineraryEntity(id = itineraryId, trip_Id = trip_Id, title = title, date = date, time = time, location = location, notes = notes)


fun TripEntity.toDomain(itinerary: List<Itinerary>): Trip =
    Trip(tripId = id, title = title, destination = destination, startDate = startDate, endDate = endDate, itinerary = itinerary)

fun ItineraryEntity.toDomain(): Itinerary =
    Itinerary(itineraryId = id, trip_Id = trip_Id, title = title, date = date, time = time, location = location, notes = notes)