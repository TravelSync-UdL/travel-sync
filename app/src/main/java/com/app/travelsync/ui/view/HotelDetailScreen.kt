package com.app.travelsync.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.R
import com.app.travelsync.ui.components.RoomImageCarouselWithControls
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailScreen(
    hotelId: String,
    groupId: String,
    start: String,
    end: String,
    navController: NavHostController,
    vm: HotelDetailViewModel = hiltViewModel()
) {
    val ui = vm.uiState.collectAsState()
    val base = "http://13.39.162.212"
    var showConfirmation by remember { mutableStateOf(false) }
    var showRoomImage by remember { mutableStateOf(false) }
    var imageToShow by remember { mutableStateOf<List<String>?>(null) }

    val dateFormat = DateTimeFormatter.ISO_DATE
    val nights = ChronoUnit.DAYS.between(LocalDate.parse(start, dateFormat), LocalDate.parse(end, dateFormat)).toInt()

    LaunchedEffect(hotelId) {
        vm.load(hotelId, groupId, start, end)
    }

    val selectedRoom = ui.value.selectedRoom
    val hotelId = ui.value.hotel?.id
    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text((ui.value.hotel?.name + " ($hotelId)") ?: "Hotel")  },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.stay, start, end, nights),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item {
                ui.value.hotel?.imageUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(base + it),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            items(ui.value.rooms ?: emptyList()) { room ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(room.roomType + " (${room.id})", fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.price_per_night_hotel, room.price))


                        Spacer(Modifier.height(8.dp))

                        room.images?.firstOrNull()?.let { img ->
                            Image(
                                painter = rememberAsyncImagePainter(base + img),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clickable {
                                        imageToShow = room.images.map { base + it }
                                        showRoomImage = true
                                    }
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        val total = room.price * nights
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Column {
                                Text(
                                    stringResource(R.string.total_price, total),
                                    fontWeight = FontWeight.ExtraBold,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = {
                                vm.selectRoom(room)
                                showConfirmation = true
                            },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.backgroundIcon),
                                    contentColor = Color.White
                                )) {
                                Text(stringResource(R.string.reserve))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showRoomImage && imageToShow != null) {
        RoomImageCarouselWithControls(
            images = imageToShow ?: emptyList(),
            onDismiss = { showRoomImage = false }
        )
    }

    if (showConfirmation && selectedRoom != null) {
        val total = selectedRoom.price * nights
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmation = false
                    vm.reserveRoom(selectedRoom)
                    navController.popBackStack()
                }) {
                    Text(stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmation = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = { Text(stringResource(R.string.confirm_reservation)) },
            text = {
                Column {
                    Text(stringResource(R.string.reservation_hotel, ui.value.hotel?.name ?: "N/A", ui.value.hotel?.id ?: "N/A"))
                    Text(stringResource(R.string.reservation_room, selectedRoom.roomType, selectedRoom.id))
                    Text(stringResource(R.string.reservation_price, selectedRoom.price, nights))
                    Text(stringResource(R.string.reservation_total, total))
                }
            }
        )
    }
}