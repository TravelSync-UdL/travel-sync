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
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.app.travelsync.R
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

    var selectedImageUrl by remember { mutableStateOf<String?>(null) }  // Estat per la imatge seleccionada
    var isDialogOpen by remember { mutableStateOf(false) }

    Log.d("ReseravtionDetails 1", reservationId.toString())

    LaunchedEffect(reservationId) {
       viewModel.loadReservation(reservationId)
    }

    reservation?.let { res ->
        Log.d("ReseravtionDetails 2", res.reservationId)
        LaunchedEffect(res.reservationId) {
            viewModel.loadRoomImageUrl(res.reservationId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.reservation_title)) },
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


            errorMessage?.let {
                Log.e("Error:Database", errorMessage!!)
                Text(
                    text = stringResource(R.string.reservation_error_message),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("gallery/$reservationId")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.gallery_button_text))
                }

                return@Column
            }
            Log.d("ReseravtionDetails", reservation.toString())
            reservation?.let { res ->

                roomImageUrl?.let { urls ->
                    if (urls.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            items(urls) { url ->
                                Image(
                                    painter = rememberAsyncImagePainter("http://13.39.162.212$url"),
                                    contentDescription = "Imatge habitació",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(MaterialTheme.shapes.medium)
                                        .padding(4.dp)
                                        .clickable {
                                            selectedImageUrl = url
                                            isDialogOpen = true
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    } else {
                        Text(stringResource(R.string.reservation_error_message))
                    }
                }



                Spacer(modifier = Modifier.height(8.dp))
                Divider()

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.room_type, res.roomType),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.price_per_night, res.totalPrice),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.start_date, res.startDate),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.end_date, res.endDate),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = {
                        navController.navigate("gallery/${res.tripId}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.backgroundIcon),
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.gallery_button_text))
                }
            }
        }

        selectedImageUrl?.let { imageUrl ->
            if (isDialogOpen) {
                AlertDialog(
                    onDismissRequest = { isDialogOpen = false },
                    title = null,
                    text = {
                        Image(
                            painter = rememberAsyncImagePainter("http://13.39.162.212$imageUrl"),
                            contentDescription = "Imatge ampliada",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = { isDialogOpen = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.backgroundIcon),
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.close_button_text))
                        }
                    }
                )
            }
        }
    }
}


