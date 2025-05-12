package com.app.travelsync.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.R
import com.app.travelsync.domain.model.Reservation


@Composable
fun ReservationRow(
    res: Reservation,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {

    var showHotelDialog by remember { mutableStateOf(false) }
    var showRoomCarousel by remember { mutableStateOf(false) }
    var askDelete by remember { mutableStateOf(false) }

    val base = "http://13.39.162.212"
    val hotelImg = base + (res.hotel?.imageUrl ?: "")
    val roomImages = res.room?.images?.map { base + it }.orEmpty()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(12.dp)
        ) {


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberAsyncImagePainter(hotelImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clickable { showHotelDialog = true }
                )


                if (roomImages.isNotEmpty()) {
                    IconButton(
                        onClick = { showRoomCarousel = true },
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.PhotoLibrary,
                            contentDescription = "Room images"
                        )
                    }
                }
            }


            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    "${res.hotelId} – Room ${res.roomId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text("${res.startDate} → ${res.endDate}")
                Text("${stringResource(R.string.price_prefix)}${res.room?.price ?: 0} €")
                Spacer(Modifier.height(8.dp))
                Text(res.guestName)
            }

            IconButton(onClick = { askDelete = true }) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
    }


    if (showHotelDialog) {
        AlertDialog(
            onDismissRequest = { showHotelDialog = false },
            confirmButton = {},
            text = {
                Image(
                    painter = rememberAsyncImagePainter(hotelImg),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        )
    }


    if (showRoomCarousel) {
        AlertDialog(
            onDismissRequest = { showRoomCarousel = false },
            confirmButton = {},
            text = {
                RoomImageCarousel(
                    images = roomImages,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }

    if (askDelete) {
        AlertDialog(
            onDismissRequest = { askDelete = false },
            confirmButton = {
                TextButton(onClick = {
                    askDelete = false
                    onCancel()
                }) { Text(stringResource(R.string.yes)) }
            },
            dismissButton = {
                TextButton(onClick = { askDelete = false }) { Text(stringResource(R.string.no)) }
            },
            title = { Text(stringResource(R.string.cancel_reservation_title)) },
            text  = { Text(stringResource(R.string.cancel_reservation_text)) }
        )
    }
}

