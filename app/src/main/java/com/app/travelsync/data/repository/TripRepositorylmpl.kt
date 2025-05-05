package com.app.travelsync.data.repository

import android.util.Log
import com.app.travelsync.data.local.mapper.toEntity
import com.app.travelsync.data.local.mapper.toDomain
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.domain.repository.TripRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositorylmpl @Inject constructor(
    private val tripDao: TripDao,
    private val itineraryDao: ItineraryDao
) : TripRepository {

    //Aixo ara no o necessitem, perque farem servir la base de dades.
    //private val trips = mutableListOf<Trip>()
    //private val itinerarys = mutableListOf<Itinerary>()

    // Trip
    override suspend fun getTrip(): List<Trip> {
        Log.d("Database", "Fetching all trips from database")
        val tripEntities = tripDao.getTrip()
        Log.d("Database", "Fetched ${tripEntities.size} trips")

        return tripEntities.map { tripEntity ->
            Log.d("Database", "Processing trip ID: ${tripEntity.id}")
            val subs = itineraryDao.getItinerary(tripEntity.id).map { it.toDomain() }
            tripEntity.toDomain(subs)
        }
    }

    override suspend fun getTripId(trip_Id: Int): Trip {
        Log.d("Database", "Fetching trip with ID: $trip_Id")
        val tripEntity = tripDao.getTripId(trip_Id)

        // Comprovem si el tripEntity és null
        if (tripEntity != null) {
            Log.d("Database", "Fetched trip: ${tripEntity.title}")
            val subs = itineraryDao.getItinerary(tripEntity.id).map { it.toDomain() }
            return tripEntity.toDomain(subs)
        } else {
            Log.e("Database", "Trip not found with ID: $trip_Id")
            throw IllegalArgumentException("Trip not found!")
        }
    }

    override suspend fun addTrip(trip: Trip) {
        Log.d("Database", "Trying to add trip: ${trip.title}")
        val existingTrip = tripDao.checkTripName(trip.title)
        if (existingTrip != null) {
            Log.e("Database", "Trip name already exists: ${trip.title}")
            throw IllegalArgumentException("El nom del viatge ja existeix!")
        }
        tripDao.addTrip(trip.toEntity())
        Log.d("Database", "Trip added successfully: ${trip.title}")
    }

    override suspend fun deleteTrip(trip_Id: Int) {
        Log.d("Database", "Deleting trip with ID: $trip_Id")
        tripDao.deleteTrip(trip_Id)
        Log.d("Database", "Trip deleted successfully")
    }

    override suspend fun editTrip(trip: Trip) {
        Log.d("Database", "Editing trip: ${trip.tripId} - ${trip.title}")
        tripDao.editTrip(trip.toEntity())
        Log.d("Database", "Trip edited successfully")
    }



    // Itinerary
    override suspend fun addActivity(itinerary: Itinerary) {
        Log.d("Database", "Adding itinerary for trip ID: ${itinerary.trip_Id}")
        itineraryDao.addItinerary(itinerary.toEntity())
        Log.d("Database", "Itinerary added successfully")
    }

    override suspend fun getActivity(trip_Id: Int): List<Itinerary> {
        Log.d("Database", "Fetching itinerary for trip ID: $trip_Id")
        val itinerary = itineraryDao.getItinerary(trip_Id).map { it.toDomain() }
        Log.d("Database", "Fetched ${itinerary.size} itinerary items")
        return itinerary
    }

    override suspend fun editActivity(itinerary: Itinerary) {
        Log.d("Database", "Editing itinerary ID: ${itinerary.itineraryId}")
        itineraryDao.editItinerary(itinerary.toEntity())
        Log.d("Database", "Itinerary edited successfully")
    }

    override suspend fun deleteActivity(itinerary_id: Int) {
        Log.d("Database", "Deleting itinerary ID: $itinerary_id")
        itineraryDao.deleteItinerary(itinerary_id)
        Log.d("Database", "Itinerary deleted successfully")
    }

    override suspend fun getTripsForUser(userId: String): List<Trip> {
        Log.d("Database", "Fetching trips for user with ID: $userId")
        val tripEntities = tripDao.getTripsForUser(userId) // Assegura't que el dao té aquest mètode
        Log.d("Database", "Fetched ${tripEntities.size} trips for user")

        return tripEntities.map { tripEntity ->
            Log.d("Database", "Processing trip ID: ${tripEntity.id}")
            val subs = itineraryDao.getItinerary(tripEntity.id).map { it.toDomain() }
            tripEntity.toDomain(subs)
        }
    }
}