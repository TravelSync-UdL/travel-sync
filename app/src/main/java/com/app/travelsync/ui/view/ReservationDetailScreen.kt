package com.app.travelsync.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.ui.viewmodel.ReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationDetailScreen(
    reservationId: Int,
    navController: NavController,
    viewModel: ReservationViewModel = hiltViewModel()
) {
    val reservation by viewModel.reservation.collectAsState()
    val errorMessage by viewModel.error.collectAsState()
    val roomImageUrl by viewModel.roomImageUrl.collectAsState()

    // Cridar a la funció per carregar la reserva per ID
    LaunchedEffect(reservationId) {
        viewModel.loadReservation(reservationId) // Usant el nom de funció loadReservation
    }

    reservation?.let { res ->
        LaunchedEffect(res.reservationId) {
            // Cridar a loadRoomImageUrl amb el res.reservationId (l'ID de la reserva)
            viewModel.loadRoomImageUrl(res.reservationId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalls de la reserva") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Enrere")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {

            // Mostra error si n'hi ha
            errorMessage?.let { error ->
                //Text("Error: $error")
                Text("Aquest viatge no te cap resserva creada")
            }

            // Mostrar la informació de la reserva si està disponible
            reservation?.let { res ->
                Text("Nom Hotel: ${res.hotelName}")
                Text("Tipus Habitacio: ${res.roomType}")
                Text("Preu: ${res.price} €")
                Text("Start Date: ${res.startDate}")
                Text("End Date: ${res.endDate}")
                roomImageUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter("http://13.39.162.212$url"),
                        contentDescription = "Imatge habitació",
                        modifier = Modifier.fillMaxWidth().height(160.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            } ?: Text("")
        }
    }
}

