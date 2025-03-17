package com.app.travelsync.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


    /**
     * Per poder gestionar la lògica de negoci i el seu estat de l'interfície d'usuari a la pantalla "Activites"
     */
@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val repository: TripRepository,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    val tripId: Int = savedStateHandle["tripId"] ?: 0

    private val _itinerarys = mutableStateListOf<Itinerary>()
    val itinerarys: List<Itinerary> get() = _itinerarys

    init {
        loadItinerary()
    }

    private fun loadItinerary() {
        _itinerarys.clear()
        _itinerarys.addAll(repository.getActivity(tripId))
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

}