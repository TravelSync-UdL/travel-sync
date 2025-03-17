package com.app.travelsync.domain.repository

import android.util.Log
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip
import javax.inject.Inject
import javax.inject.Singleton

// Funció per gestionar logs de manera segura
fun safeLog(tag: String, message: String) {
    val isJUnitTest = System.getProperty("java.class.path")?.contains("junit") == true

    if (isJUnitTest) {
        println("$tag: $message") // En tests unitàries, imprimeix en consola
    } else {
        Log.d(tag, message) // En Android, mostra a Logcat
    }
}

@Singleton
class TripRepositorylmpl @Inject constructor() : TripRepository {

    private val trips = mutableListOf<Trip>()
    private val itinerarys = mutableListOf<Itinerary>()

    // Trip
    override fun getTrip(): List<Trip> {
        safeLog("TripRepository", "Obtenint tots els viatges...")
        return trips.map { trip ->
            trip.copy(itinerary = itinerarys.filter { it.trip_Id == trip.tripId })
        }
    }

    override fun addTrip(trip: Trip) {
        val newTrip = trip.copy(tripId = trips.size + 1)
        trips.add(newTrip)
        safeLog("TripRepository", "Viatge afegit: ${newTrip.tripId} - ${newTrip.title}")
    }

    override fun deleteTrip(trip_Id: Int) {
        val removed = trips.removeAll { it.tripId == trip_Id }
        itinerarys.removeAll { it.trip_Id == trip_Id }
        if (removed) {
            safeLog("TripRepository", "Viatge eliminat amb ID: $trip_Id")
        } else {
            safeLog("TripRepository", "Error: No s'ha trobat el viatge amb ID $trip_Id")
        }
    }

    override fun editTrip(trip: Trip) {
        val index = trips.indexOfFirst { it.tripId == trip.tripId }
        if (index != -1) {
            trips[index] = trip
            safeLog("TripRepository", "Viatge editat: ${trip.tripId} - ${trip.title}")
        } else {
            safeLog("TripRepository", "Error: No es pot editar un viatge inexistent (ID: ${trip.tripId})")
        }
    }

    // Itinerary
    override fun addActivity(itinerary: Itinerary) {
        val newItinerary = itinerary.copy(itineraryId = itinerarys.size + 1)
        itinerarys.add(newItinerary)
        safeLog("TripRepository", "Itinerari afegit: ${newItinerary.itineraryId} - ${newItinerary.title}")
    }

    override fun getActivity(trip_Id: Int): List<Itinerary> {
        safeLog("TripRepository", "Obtenint itineraris per al viatge ID: $trip_Id")
        return itinerarys.filter { it.trip_Id == trip_Id }
    }

    override fun editActivity(itinerary: Itinerary) {
        val index = itinerarys.indexOfFirst { it.itineraryId == itinerary.itineraryId }
        if (index != -1) {
            itinerarys[index] = itinerary
            safeLog("TripRepository", "Itinerari editat: ${itinerary.itineraryId} - ${itinerary.title}")
        } else {
            safeLog("TripRepository", "Error: No es pot editar un itinerari inexistent (ID: ${itinerary.itineraryId})")
        }
    }

    override fun deleteActivity(itinerary_id: Int) {
        val removed = itinerarys.removeAll { it.itineraryId == itinerary_id }
        if (removed) {
            safeLog("TripRepository", "Itinerari eliminat amb ID: $itinerary_id")
        } else {
            safeLog("TripRepository", "Error: No s'ha trobat l'itinerari amb ID $itinerary_id")
        }
    }
}
