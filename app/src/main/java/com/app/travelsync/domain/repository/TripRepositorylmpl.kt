package com.app.travelsync.domain.repository

import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositorylmpl @Inject constructor() : TripRepository{

    private val trips = mutableListOf<Trip>()
    private val itinerarys = mutableListOf<Itinerary>()


    //Trip
    override fun getTrip(): List<Trip> {
        return trips.map { trip ->
            trip.copy(subTasks = itinerarys.filter { it.trip_Id == trip.tripId })
        }
    }

    override fun addTrip(trip: Trip) {
        val newTask = trip.copy(tripId = trips.size + 1)
        trips.add(newTask)
    }

    override fun deleteTrip(trip_Id: Int) {
        trips.removeAll { it.tripId == trip_Id }
        itinerarys.removeAll { it.trip_Id == trip_Id }
    }

    override fun editTrip(trip: Trip) {
        val index = trips.indexOfFirst { it.tripId == trip.tripId }
        if (index != -1) {
            trips[index] = trip
        }
    }

    //Itinerary

    override fun addActivity(itinerary: Itinerary) {
        val newSubTask = itinerary.copy(itineraryId = itinerarys.size + 1)
        itinerarys.add(newSubTask)
    }

    override fun getActivity(trip_Id: Int): List<Itinerary> {
        return itinerarys.filter { it.trip_Id == trip_Id }
    }

    override fun editActivity(itinerary: Itinerary) {
        val index = itinerarys.indexOfFirst { it.itineraryId == itinerary.itineraryId }
        if (index != -1) {
            itinerarys[index] = itinerary
        }
    }

    override fun deleteActivity(itinerary_id: Int) {
        itinerarys.removeAll { it.itineraryId == itinerary_id }
    }

}