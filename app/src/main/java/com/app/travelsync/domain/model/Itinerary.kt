package com.app.travelsync.domain.model

data class Itinerary (
    val itineraryId: Int = 0,
    val trip_Id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val notes: String,
)