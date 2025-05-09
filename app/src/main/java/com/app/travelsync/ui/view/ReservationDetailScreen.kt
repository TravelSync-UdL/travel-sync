package com.app.travelsync.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
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

    LaunchedEffect(reservationId) {
        viewModel.loadReservation(reservationId)
    }

    reservation?.let { res ->
        LaunchedEffect(res.reservationId) {
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Missatge d'error si n'hi ha
            errorMessage?.let {
                Text(
                    text = "Aquest viatge no té cap reserva creada",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                return@Column
            }

            reservation?.let { res ->
                // Imatge de l'habitació
                roomImageUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter("http://13.39.162.212$url"),
                        contentDescription = "Imatge habitació",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 16.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }

                // Detalls textuals
                Text(
                    text = res.hotelName,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider()

                Spacer(modifier = Modifier.height(8.dp))
                Text("🏨 Tipus d'habitació: ${res.roomType}", style = MaterialTheme.typography.bodyLarge)
                Text("💰 Preu: ${res.price} €", style = MaterialTheme.typography.bodyLarge)
                Text("📅 Inici: ${res.startDate}", style = MaterialTheme.typography.bodyLarge)
                Text("📅 Final: ${res.endDate}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(24.dp))

                // Botó d'acció futur (placeholder)
                Button(
                    onClick = {
                        navController.navigate("gallery/${res.tripId}") // Assegura’t de tenir el tripId disponible
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Galeria")
                }
            } ?: Text("Carregant...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


