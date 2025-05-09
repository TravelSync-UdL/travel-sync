package com.app.travelsync.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.data.repository.GalleryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
): ViewModel(){

    val trips = galleryRepository.trips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addImage(tripId: Int, uri: Uri) = viewModelScope.launch {
        galleryRepository.addImage(tripId, uri)
    }

    fun deleteImage(uri: Uri) = viewModelScope.launch {
        galleryRepository.deleteImageByUri(uri)
    }
}