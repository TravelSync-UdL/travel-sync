package com.app.travelsync.ui.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.domain.model.Itinerary
import com.app.travelsync.ui.viewmodel.ItineraryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    navController: NavController,
    viewModel: ItineraryViewModel = hiltViewModel()
){

    val itinerarys = viewModel.itinerarys

    // Estados para el diálogo de edición/creación
    var showDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var currentItineraryId by remember { mutableStateOf(0) }
    var itineraryTitle by remember { mutableStateOf("") }
    var itineraryDate by remember { mutableStateOf("") }
    var itineraryTime by remember { mutableStateOf("") }
    var itineraryLocation by remember { mutableStateOf("") }
    var itineraryNotes by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    val openDatePicker: (Boolean) -> Unit = { isStartDate ->
        DatePickerDialog(
            navController.context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val formattedDate = dateFormatter.format(calendar.time)
                itineraryDate = formattedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    val openTimePicker: (Boolean) -> Unit = { isStartTime ->
        TimePickerDialog(
            navController.context,
            { _: TimePicker, hourOfDay: Int, minute: Int ->
                val formattedTime = timeFormatter.format(calendar.apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }.time)
                itineraryTime = formattedTime
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    fun isFormValid(): Boolean {
        return itineraryTitle.isNotEmpty() &&
                itineraryDate != "" &&
                itineraryTime != "" &&
                itineraryLocation.isNotEmpty()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.activities)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("trip") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
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
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(itinerarys) { itinerary ->
                        ItineraryItem(
                            itinerary = itinerary,
                            onEdit = {
                                isEditing = true
                                currentItineraryId = itinerary.itineraryId
                                itineraryTitle = itinerary.title
                                itineraryDate = itinerary.date
                                itineraryTime = itinerary.time
                                itineraryLocation = itinerary.location
                                itineraryNotes = itinerary.notes
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteItinerary(itinerary.itineraryId) }
                        )
                    }
                }

                // Botó substituït del FloatingActionButton
                Button(
                    onClick = {
                        // Configurar per afegir una nova subtarea
                        isEditing = false
                        itineraryTitle = ""
                        itineraryDate = ""
                        itineraryTime = ""
                        itineraryLocation = ""
                        itineraryNotes = ""
                        showDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.backgroundIcon), // Color de fons
                        contentColor = Color.White // Color del text
                    )
                ) {
                    Text(stringResource(id = R.string.add_activity)) // El text dins del botó
                }
            }
        }
    )

    // Diàleg de creació/edició d'activitat
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (isEditing) "Editar Activitat" else "Nova Activitat") },
            text = {
                Column {
                    OutlinedTextField(
                        value = itineraryTitle,
                        onValueChange = { itineraryTitle = it },
                        label = { Text(stringResource(id = R.string.title)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { openDatePicker(true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.backgroundIcon), // Color de fons
                            contentColor = Color.White // Color del text
                        )
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Calendari")
                        Text(
                            text = "${stringResource(id = R.string.date)} $itineraryDate",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Button(
                        onClick = { openTimePicker(true) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.backgroundIcon), // Color de fons
                            contentColor = Color.White // Color del text
                        )
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Time")
                        Text(
                            text = "${stringResource(id = R.string.time)} $itineraryTime",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = itineraryLocation,
                        onValueChange = { itineraryLocation = it },
                        label = { Text(stringResource(id = R.string.location)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = itineraryNotes,
                        onValueChange = { itineraryNotes = it },
                        label = { Text(stringResource(id = R.string.notes)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isEditing) {
                            viewModel.editItinerary(
                                Itinerary(
                                    itineraryId = currentItineraryId,
                                    trip_Id = viewModel.tripId,
                                    title = itineraryTitle,
                                    date = itineraryDate,
                                    time = itineraryTime,
                                    location = itineraryLocation,
                                    notes = itineraryNotes
                                )
                            )
                        } else {
                            viewModel.addItinerary(
                                Itinerary(
                                    trip_Id = viewModel.tripId,
                                    title = itineraryTitle,
                                    date = itineraryDate,
                                    time = itineraryTime,
                                    location = itineraryLocation,
                                    notes = itineraryNotes
                                )
                            )
                        }
                        showDialog = false
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
                    onClick = { showDialog = false },
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
fun ItineraryItem(
    itinerary: Itinerary,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    // Contenedor principal con fondo y borde redondeado
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clip(MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        // Títol de la subtarea
        Text(
            text = itinerary.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Fecha y hora de la subtarea
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Fecha",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "${stringResource(id = R.string.date_button)}: ${itinerary.date} - ${stringResource(id = R.string.time_button)}: ${itinerary.time}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Ubicación de la subtarea
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Ubicación",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "${stringResource(id = R.string.location)}: ${itinerary.location}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Notas de la subtarea
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Notas",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "${stringResource(id = R.string.notes)}: ${itinerary.notes}",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Separador para mejorar la legibilidad
        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Iconos de editar y eliminar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            // Botón para eliminar la subtarea
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar Activitat",
                    tint = colorResource(id = R.color.backgroundIcon)
                )
            }

            // Botón para editar la subtarea
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Editar Activitat",
                    tint = colorResource(id = R.color.backgroundIcon)
                )
            }
        }
    }
}