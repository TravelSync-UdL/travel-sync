package com.app.travelsync.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.app.travelsync.data.local.entity.ImageEntity
import com.app.travelsync.data.local.entity.TripEntity


data class TripWithImages(
    @Embedded val trip: TripEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId"
    )
    val images: List<ImageEntity>
)