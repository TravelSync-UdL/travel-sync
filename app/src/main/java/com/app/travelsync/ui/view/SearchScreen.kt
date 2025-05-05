package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.tools.build.jetifier.core.utils.Log
import com.app.travelsync.R
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.ui.viewmodel.SearchViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.domain.model.Room

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val hotels = viewModel.hotelList
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.explore_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Eliminem l'OutlindTextField ja que no filtrarem
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> {
                Log.d("SearchScreen", "Error message: $errorMessage") // <--- Afegit
                Text("Error: $errorMessage")
            }
            else -> {
                // Mostrar tots els hotels sense filtrar
                HotelList(hotels = hotels)
            }
        }
    }
}


@Composable
fun HotelList(hotels: List<Hotel>) {
    LazyColumn {
        items(hotels) { hotel ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = hotel.name, style = MaterialTheme.typography.titleMedium)
                Text(text = hotel.address)
                Text(text = "Rating: ${hotel.rating ?: "N/A"}")

                Image(
                    painter = rememberAsyncImagePainter("http://13.39.162.212${hotel.image_url}"),
                    contentDescription = "Hotel image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                hotel.rooms.forEach { room ->
                    RoomItem(room = room)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
            }
        }
    }
}

@Composable
fun RoomItem(room: Room) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(text = "Room: ${room.room_type}, Price: ${room.price}€")

        room.images.forEach { image ->
            Image(
                painter = rememberAsyncImagePainter("http://13.39.162.212$image"),
                contentDescription = "Room image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(vertical = 4.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
