package com.app.travelsync.domain.repository

import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip

interface TripRepository {
    fun getTrip(): List<Trip>
    fun addTrip(trip: Trip)
    fun deleteTrip(trip_Id: Int)
    fun editTrip(trip: Trip)

    fun getActivity(trip_Id: Int): List<Itinerary>
    fun addActivity(itinerary: Itinerary)
    fun deleteActivity(itinerary_id: Int)
    fun editActivity(itinerary: Itinerary)
}