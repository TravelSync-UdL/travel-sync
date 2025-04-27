package com.app.travelsync

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.travelsync.data.local.AppDatabase
import com.app.travelsync.data.local.entity.TripEntity
import com.app.travelsync.data.local.dao.TripDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var tripDao: TripDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        tripDao = database.tripDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertTripAndGetById() = runBlocking {
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "05/06/2025",
            endDate = "15/06/2025",
            ownerLogin = "gmoli"
        )
        tripDao.addTrip(trip)

        val retrieved = tripDao.getTrip().first()
        assertEquals(trip.title, retrieved.title)
    }

    @Test
    fun deleteTrip() = runBlocking {
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "05/06/2025",
            endDate = "15/06/2025",
            ownerLogin = "gmoli"
        )
        tripDao.addTrip(trip)
        tripDao.deleteTrip(1)

        val trips = tripDao.getTrip()
        assertTrue(trips.isEmpty())
    }

    @Test
    fun updateTrip() = runBlocking {
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "05/06/2025",
            endDate = "15/06/2025",
            ownerLogin = "gmoli"
        )
        tripDao.addTrip(trip)

        val updatedTrip = trip.copy(title = "Updated Trip")
        tripDao.editTrip(updatedTrip)

        val retrieved = tripDao.getTrip().first()
        assertEquals("Updated Trip", retrieved.title)
    }

    @Test
    fun checkTripNameExists() = runBlocking {
        val trip = TripEntity(
            id = 1,
            title = "Test Trip",
            destination = "Barcelona",
            startDate = "05/06/2025",
            endDate = "15/06/2025",
            ownerLogin = "gmoli"
        )
        tripDao.addTrip(trip)

        val existingTrip = tripDao.checkTripName("Test Trip")
        assertNotNull(existingTrip)

        val nonExistingTrip = tripDao.checkTripName("Non Existing Trip")
        assertNull(nonExistingTrip)
    }
}