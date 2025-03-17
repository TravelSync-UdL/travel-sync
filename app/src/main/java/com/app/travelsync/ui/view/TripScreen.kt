package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.ui.viewmodel.TripViewModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.app.travelsync.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


/**
 * Classe per poder controlar i que es mostri la UI de la pantalla Trip
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripScreen(
    navController: NavController,
    viewModel: TripViewModel = hiltViewModel()
) {
    val trips = viewModel.trips
    var showTripDialog by remember { mutableStateOf(false) }
    var isEditingTrip by remember { mutableStateOf(false) }
    var isDeletingTrip by remember { mutableStateOf(false) }
    var currentTripId by remember { mutableStateOf(0) }
    var tripTitle by remember { mutableStateOf("") }
    var tripDestination by remember { mutableStateOf("") }
    var tripStartDate by remember { mutableStateOf("") }
    var tripEndDate by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    //Que es mostri el calendari a partir d'avui
    val openDatePicker: (Boolean) -> Unit = { isStartDate ->
        val today = Calendar.getInstance()

        val minDate = if (isStartDate) today.timeInMillis else calendar.timeInMillis

        DatePickerDialog(
            navController.context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val formattedDate = dateFormatter.format(calendar.time)
                if (isStartDate) {
                    tripStartDate = formattedDate
                } else {
                    tripEndDate = formattedDate
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = minDate
        }.show()
    }

    fun isFormValid(): Boolean {
        return tripTitle.isNotEmpty() &&
                tripDestination.isNotEmpty() &&
                tripStartDate != "" &&
                tripEndDate != ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(stringResource(id = R.string.trip_screen_title)) },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyColumn {
                    items(trips) { trip ->
                        TripItem(
                            trip = trip,
                            onEdit = {
                                isEditingTrip = true
                                isDeletingTrip = false
                                currentTripId = trip.tripId
                                tripTitle = trip.title
                                tripDestination = trip.destination
                                tripStartDate = trip.startDate
                                tripEndDate = trip.endDate
                                showTripDialog = true
                            },
                            onOpen = {
                                navController.navigate("itinerarys/${trip.tripId}")
                            },
                            onDelete = { viewModel.deleteTrip(trip.tripId) }
                        )
                    }
                }
                Button(
                    onClick = {
                        isEditingTrip = false
                        tripTitle = ""
                        tripDestination = ""
                        tripStartDate = ""
                        tripEndDate = ""
                        showTripDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.backgroundIcon),
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.add_trip))
                }
            }
        }
    )

    if (showTripDialog) {
        AlertDialog(
            onDismissRequest = { showTripDialog = false },
            title = {
                Text(
                    text = if (isEditingTrip) stringResource(id = R.string.edit_trip)
                    else stringResource(id = R.string.new_trip),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(modifier = Modifier.padding(8.dp)) {

                    OutlinedTextField(
                        value = tripTitle,
                        onValueChange = { tripTitle = it },
                        label = { Text(stringResource(id = R.string.trip_title))},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )


                    OutlinedTextField(
                        value = tripDestination,
                        onValueChange = { tripDestination = it },
                        label = { Text(stringResource(id = R.string.trip_destination)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )


                    Button(
                        onClick = { openDatePicker(true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.backgroundIcon),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Calendari")
                        Text(
                            text = stringResource(id = R.string.trip_start_date, tripStartDate),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    //L'opció de triar el dia per acabar el viatge, només es podrà fer si ja s'ha triat el dia d'inici.
                    if (tripStartDate != "") {
                        Button(
                            onClick = { openDatePicker(false) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.backgroundIcon),
                                contentColor = Color.White
                            ),
                            enabled = tripStartDate != ""
                        ) {
                            Icon(Icons.Filled.Edit, contentDescription = "Calendari")
                            Text(
                                text = stringResource(id = R.string.trip_end_date, tripEndDate),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isEditingTrip) {
                            viewModel.editTrip(
                                Trip(
                                    tripId = currentTripId,
                                    title = tripTitle,
                                    destination = tripDestination,
                                    startDate = tripStartDate,
                                    endDate = tripEndDate
                                )
                            )
                        } else {
                            viewModel.addTrip(
                                Trip(
                                    title = tripTitle,
                                    destination = tripDestination,
                                    startDate = tripStartDate,
                                    endDate = tripEndDate
                                )
                            )
                        }
                        showTripDialog = false
                    },
                    enabled = isFormValid(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.backgroundIcon),
                        contentColor = Color.White
                    ),
                ) {
                    Text(stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showTripDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.backgroundIcon),
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}



@Composable
fun TripItem(
    trip: Trip,
    onEdit: () -> Unit,
    onOpen: () -> Unit,
    onDelete: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {

        Text(
            text = trip.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Destinació",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.trip_destination_label, trip.destination),
                style = MaterialTheme.typography.bodyLarge
            )
        }


        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Dates",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.trip_dates_label, trip.startDate, trip.endDate),
                style = MaterialTheme.typography.bodyLarge
            )
        }


        Divider(modifier = Modifier.padding(vertical = 8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {


            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar Viatge",
                    tint = colorResource(id = R.color.backgroundIcon)
                )
            }


            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Viatge",
                    tint = colorResource(id = R.color.backgroundIcon)
                )
            }

            IconButton(onClick = onOpen) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Obrir Subtareas",
                    tint = colorResource(id = R.color.backgroundIcon)
                )
            }
        }
    }
}
