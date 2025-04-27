package com.app.travelsync.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val repository: TripRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    val tripId: Int = savedStateHandle["taskId"] ?: 0

    private val _tripDates = mutableStateOf(Pair("", ""))
    val tripDates: Pair<String, String> get() = _tripDates.value

    private val _itinerarys = mutableStateListOf<Itinerary>()
    val itinerarys: List<Itinerary> get() = _itinerarys

    init {
        loadItinerary()
        loadTripDates()
    }

    private fun loadItinerary() {
        _itinerarys.clear()
        viewModelScope.launch {
            _itinerarys.addAll(repository.getActivity(tripId))
        }
    }

    fun addItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            repository.addActivity(itinerary)
            loadItinerary()
        }
    }

    fun deleteItinerary(itineraryId: Int) {
        viewModelScope.launch {
            repository.deleteActivity(itineraryId)
            loadItinerary()
        }
    }

    fun editItinerary(editItinerary: Itinerary) {
        viewModelScope.launch {
            repository.editActivity(editItinerary)
            loadItinerary()
        }
    }

    private fun loadTripDates() {
        viewModelScope.launch {
            // Aquí recuperem el "trip" per obtenir les dates.
            val trip = repository.getTripId(tripId)
            _tripDates.value = Pair(trip.startDate, trip.endDate)
        }
    }

}