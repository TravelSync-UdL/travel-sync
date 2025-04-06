package com.app.travelsync

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.travelsync.data.local.AppDatabase
import com.app.travelsync.data.local.entity.ItineraryEntity
import com.app.travelsync.data.local.dao.ItineraryDao
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.data.local.entity.TripEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class ItineraryDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var itineraryDao: ItineraryDao
    private lateinit var tripDao: TripDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        itineraryDao = database.itineraryDao()
        tripDao = database.tripDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertItineraryAndGetByTripId() = runBlocking {
        // First insert a trip
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "25/06/2025",
            endDate = "30/06/2025"
        )
        tripDao.addTrip(trip)

        // Then insert itinerary for that trip
        val itinerary = ItineraryEntity(
            id = 1,
            trip_Id = 1,
            title = "Day 1",
            date = "26/06/2025",
            time = "12:30",
            location = "Barcelona",
            notes = ""
        )
        itineraryDao.addItinerary(itinerary)

        val itineraries = itineraryDao.getItinerary(1)
        assertEquals(1, itineraries.size)
        assertEquals("Day 1", itineraries[0].title)
    }

    @Test
    fun deleteItinerary() = runBlocking {
        // First insert a trip
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "25/06/2025",
            endDate = "30/06/2025"
        )
        tripDao.addTrip(trip)

        // Then insert itinerary
        val itinerary = ItineraryEntity(
            id = 1,
            trip_Id = 1,
            title = "Day 1",
            date = "26/06/2025",
            time = "12:30",
            location = "Barcelona",
            notes = ""
        )
        itineraryDao.addItinerary(itinerary)
        itineraryDao.deleteItinerary(1)

        val itineraries = itineraryDao.getItinerary(1)
        assertTrue(itineraries.isEmpty())
    }

    @Test
    fun updateItinerary() = runBlocking {
        // First insert a trip
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "25/06/2025",
            endDate = "30/06/2025"
        )
        tripDao.addTrip(trip)

        // Then insert itinerary
        val itinerary = ItineraryEntity(
            id = 1,
            trip_Id = 1,
            title = "Day 1",
            date = "26/06/2025",
            time = "12:30",
            location = "Barcelona",
            notes = ""
        )
        itineraryDao.addItinerary(itinerary)

        // Update itinerary
        val updatedItinerary = itinerary.copy(title = "Updated Day 1")
        itineraryDao.editItinerary(updatedItinerary)

        val retrieved = itineraryDao.getItinerary(1).first()
        assertEquals("Updated Day 1", retrieved.title)
    }
}