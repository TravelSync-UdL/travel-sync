package com.app.travelsync.data.repository

import android.net.Uri
import com.app.travelsync.data.local.TripWithImages
import com.app.travelsync.data.local.dao.TripDao
import com.app.travelsync.data.local.entity.ImageEntity
import com.app.travelsync.domain.model.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val dao: TripDao
) {


    val trips: Flow<List<Trip>> = dao.getTripsWithImages()
        .map { list -> list.map { it.toTrip() } }


    suspend fun addImage(tripId: Int, uri: Uri) {
        dao.insertImage(ImageEntity(tripId = tripId, uri = uri.toString()))
    }



    private fun TripWithImages.toTrip() = Trip(
        tripId = trip.id,
        title = trip.title,
        destination = trip.destination,
        startDate = trip.startDate,
        endDate = trip.endDate,
        ownerLogin = trip.ownerLogin,
        images = images.map { Uri.parse(it.uri) }.toMutableList()
    )

    suspend fun deleteImageByUri(uri: Uri) {
        dao.deleteImageByUri(uri.toString())
        File(uri.path!!).delete()
    }
}