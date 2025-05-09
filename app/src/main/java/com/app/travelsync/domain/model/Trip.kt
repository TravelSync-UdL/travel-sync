package com.app.travelsync.domain.model

import android.net.Uri

data class Trip (
    val tripId: Int = 0,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val ownerLogin: String,
    val itinerary: List<Itinerary> = emptyList(),
    val images: MutableList<Uri> = mutableListOf()
)