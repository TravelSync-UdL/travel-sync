package com.app.travelsync.domain.repository

import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip

interface TripRepository {
    suspend fun getTrip(): List<Trip>
    suspend fun addTrip(trip: Trip)
    suspend fun deleteTrip(trip_Id: Int)
    suspend fun editTrip(trip: Trip)

    suspend fun getActivity(trip_Id: Int): List<Itinerary>
    suspend fun addActivity(itinerary: Itinerary)
    suspend fun deleteActivity(itinerary_id: Int)
    suspend fun editActivity(itinerary: Itinerary)

    suspend fun getTripsForUser(login: String): List<Trip>
    suspend fun getTripId(trip_Id: Int): Trip

}