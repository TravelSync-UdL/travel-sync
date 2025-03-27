package com.app.travelsync.data

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
        val tripEntities = tripDao.getTrip()

        return tripEntities.map { tripEntity ->
            println("ID TRIP" + tripEntity.id)
            val subs = itineraryDao.getItinerary(tripEntity.id).map { it.toDomain() }
            tripEntity.toDomain(subs)
        }
    }

    override suspend fun addTrip(trip: Trip) {
        tripDao.addTrip(trip.toEntity())
    }

    override suspend fun deleteTrip(trip_Id: Int) {
        tripDao.deleteTrip(trip_Id)
    }

    override suspend fun editTrip(trip: Trip) {
        tripDao.editTrip(trip.toEntity())
    }

    // Itinerary
    override suspend fun addActivity(itinerary: Itinerary) {
        itineraryDao.addItinerary(itinerary.toEntity())
    }

    override suspend fun getActivity(trip_Id: Int): List<Itinerary> {
        return itineraryDao.getItinerary(trip_Id).map { it.toDomain() }
    }

    override suspend fun editActivity(itinerary: Itinerary) {
        itineraryDao.editItinerary(itinerary.toEntity())
    }

    override suspend fun deleteActivity(itinerary_id: Int) {
        itineraryDao.deleteItinerary(itinerary_id)
    }
}