package com.app.travelsync
/**
 *
 * NO SON NECESSARIS PERQUE ARA FEM SERVIR UNA BASE DE DADES
 *
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.data.TripRepositorylmpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CRUDTest {
    private lateinit var tripRepository: TripRepositorylmpl

    @Before
    fun setUp() {
        tripRepository = TripRepositorylmpl()
    }

    @Test
    fun `addTrip should add a new trip to the list`() {

        val trip = Trip(tripId = 1, title = "Trip to Paris", destination = "Paris", startDate = "20/03/2025", endDate = "31/03/2025")

        tripRepository.addTrip(trip)


        val trips = tripRepository.getTrip()
        assertEquals(1, trips.size)
        assertEquals("Trip to Paris", trips[0].title)
        assertEquals("Paris", trips[0].destination)
        assertEquals("20/03/2025", trips[0].startDate)
        assertEquals("31/03/2025", trips[0].endDate)
    }

    @Test
    fun `getTrip should return all trips with their itineraries`() {

        val trip = Trip(tripId = 1, title = "Trip to Paris", destination = "Paris", startDate = "20/03/2025", endDate = "31/03/2025")
        val itinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower", date = "21/03/2025", time = "10:30", location = "Paris", notes = "")


        tripRepository.addTrip(trip)
        tripRepository.addActivity(itinerary)


        val trips = tripRepository.getTrip()
        assertEquals(1, trips.size)
        assertEquals(1, trips[0].itinerary.size)
        assertEquals("Visit Eiffel Tower", trips[0].itinerary[0].title)
        assertEquals("21/03/2025", trips[0].itinerary[0].date)
        assertEquals("10:30", trips[0].itinerary[0].time)
        assertEquals("Paris", trips[0].itinerary[0].location)
        assertEquals("", trips[0].itinerary[0].notes)
    }

    @Test
    fun `deleteTrip should remove the trip and its itineraries`() {

        val trip = Trip(tripId = 1, title = "Trip to Paris", destination = "Paris", startDate = "20/03/2025", endDate = "31/03/2025")
        val itinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower", date = "21/03/2025", time = "10:30", location = "Paris", notes = "")

        tripRepository.addTrip(trip)
        tripRepository.addActivity(itinerary)


        tripRepository.deleteTrip(1)

        val trips = tripRepository.getTrip()
        assertEquals(0, trips.size)
        val activities = tripRepository.getActivity(1)
        assertEquals(0, activities.size)
    }

    @Test
    fun `editTrip should update the trip details`() {

        val trip = Trip(tripId = 1, title = "Trip to Paris", destination = "Paris", startDate = "20/03/2025", endDate = "31/03/2025")
        tripRepository.addTrip(trip)

        val updatedTrip = Trip(tripId = 1, title = "Trip to London", destination = "London", startDate = "20/03/2025", endDate = "31/03/2025")


        tripRepository.editTrip(updatedTrip)


        val trips = tripRepository.getTrip()
        assertEquals(1, trips.size)
        assertEquals("Trip to London", trips[0].title)
        assertEquals("London", trips[0].destination)
        assertEquals("20/03/2025", trips[0].startDate)
        assertEquals("31/03/2025", trips[0].endDate)
    }

    @Test
    fun `addActivity should add a new itinerary to the list`() {

        val itinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower", date = "21/03/2025", time = "10:30", location = "Paris", notes = "")


        tripRepository.addActivity(itinerary)


        val activities = tripRepository.getActivity(1)
        assertEquals(1, activities.size)
        assertEquals("Visit Eiffel Tower", activities[0].title)
        assertEquals("21/03/2025", activities[0].date)
        assertEquals("10:30", activities[0].time)
        assertEquals("Paris", activities[0].location)
        assertEquals("", activities[0].notes)
    }

    @Test
    fun `getActivity should return all itineraries for a specific trip`() {

        val itinerary1 = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower",date = "21/03/2025", time = "10:30", location = "Paris", notes = "")
        val itinerary2 = Itinerary(itineraryId = 2, trip_Id = 1, title = "Visit Louvre Museum",date = "22/03/2025", time = "12:35", location = "Paris", notes = "")

        tripRepository.addActivity(itinerary1)
        tripRepository.addActivity(itinerary2)


        val activities = tripRepository.getActivity(1)

        assertEquals(2, activities.size)
        assertEquals("Visit Eiffel Tower", activities[0].title)
        assertEquals("21/03/2025", activities[0].date)
        assertEquals("10:30", activities[0].time)
        assertEquals("Paris", activities[0].location)
        assertEquals("", activities[0].notes)
        assertEquals("Visit Louvre Museum", activities[1].title)
        assertEquals("22/03/2025", activities[1].date)
        assertEquals("12:35", activities[1].time)
        assertEquals("Paris", activities[1].location)
        assertEquals("", activities[1].notes)
    }

    @Test
    fun `editActivity should update the itinerary details`() {

        val itinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower",date = "21/03/2025", time = "10:30", location = "Paris", notes = "")
        tripRepository.addActivity(itinerary)

        val updatedItinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Louvre Museum",date = "22/03/2025", time = "12:35", location = "Paris", notes = "")


        tripRepository.editActivity(updatedItinerary)


        val activities = tripRepository.getActivity(1)
        assertEquals(1, activities.size)
        assertEquals("Visit Louvre Museum", activities[0].title)
        assertEquals("22/03/2025", activities[0].date)
        assertEquals("12:35", activities[0].time)
        assertEquals("Paris", activities[0].location)
        assertEquals("", activities[0].notes)
    }

    @Test
    fun `deleteActivity should remove the itinerary`() {

        val itinerary = Itinerary(itineraryId = 1, trip_Id = 1, title = "Visit Eiffel Tower",date = "21/03/2025", time = "10:30", location = "Paris", notes = "")
        tripRepository.addActivity(itinerary)


        tripRepository.deleteActivity(1)


        val activities = tripRepository.getActivity(1)
        assertEquals(0, activities.size)
    }
}**/