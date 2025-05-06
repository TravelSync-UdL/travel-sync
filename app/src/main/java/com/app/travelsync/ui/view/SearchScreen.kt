package com.app.travelsync.ui.view

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Room
import com.app.travelsync.ui.viewmodel.SearchViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    var selectedCity by remember { mutableStateOf("Londres") }
    var startDate by remember { mutableStateOf("2025-05-10") }
    var endDate by remember { mutableStateOf("2025-05-12") }

    val cities = listOf("Londres", "Paris", "Barcelona")

    var startDateDialogOpen by remember { mutableStateOf(false) }
    var endDateDialogOpen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Search Hotels", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown de ciutat
        var expanded by remember { mutableStateOf(false) }
        Box {
            TextButton(onClick = { expanded = true }) {
                Text("City: $selectedCity")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                cities.forEach { city ->
                    DropdownMenuItem(onClick = {
                        selectedCity = city
                        expanded = false
                    }, text = { Text(city) })
                }
            }
        }

        // Dates amb botons per obrir DatePickers
        Row {
            Text("Start: ")
            TextButton(onClick = { startDateDialogOpen = true }) {
                Text(startDate)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text("End: ")
            TextButton(onClick = { endDateDialogOpen = true }) {
                Text(endDate)
            }
        }

        Button(onClick = {

            startDate = formatDate(startDate)
            endDate = formatDate(endDate)

            viewModel.searchHotels(selectedCity, startDate, endDate)
        }) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading) CircularProgressIndicator()
        else if (viewModel.errorMessage != null) Text("Error: ${viewModel.errorMessage}")
        else HotelList(hotels = viewModel.hotelList, onReserveClick = { room, hotel ->
            viewModel.bookRoom(hotel, room, startDate, endDate)
        })
    }

    // DatePicker Dialog for Start Date
    if (startDateDialogOpen) {
        ShowDatePickerDialog(
            onDateSelected = { day, month, year ->
                startDate = "$year-${month + 1}-$day"
                startDateDialogOpen = false
            }
        )
    }

    // DatePicker Dialog for End Date
    if (endDateDialogOpen) {
        ShowDatePickerDialog(
            minDate = formatDate(startDate),  // Passar la data mínima de StartDate
            onDateSelected = { day, month, year ->
                endDate = "$year-${month + 1}-$day"
                endDateDialogOpen = false
            }
        )
    }
}

@Composable
fun ShowDatePickerDialog(minDate: String = "", onDateSelected: (Int, Int, Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)

    // Configurar la data mínima si es proporcionada
    val minDateCalendar = if (minDate.isNotEmpty()) {
        val minDateParts = minDate.split("-")
        Calendar.getInstance().apply {
            set(minDateParts[0].toInt(), minDateParts[1].toInt() - 1, minDateParts[2].toInt())
        }
    } else {
        null
    }

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            onDateSelected(selectedDayOfMonth, selectedMonth, selectedYear)
        },
        year, month, day
    )

    // Si es passa una data mínima, establir-la en el DatePicker
    minDateCalendar?.let {
        datePickerDialog.datePicker.minDate = it.timeInMillis
    }

    DisposableEffect(Unit) {
        datePickerDialog.show()
        onDispose {
            datePickerDialog.dismiss()
        }
    }
}

@Composable
fun HotelList(hotels: List<Hotel>, onReserveClick: (Room, Hotel) -> Unit) {
    LazyColumn {
        items(hotels) { hotel ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(hotel.name, style = MaterialTheme.typography.titleMedium)
                Text(hotel.address)
                Image(
                    painter = rememberAsyncImagePainter("http://13.39.162.212${hotel.image_url}"),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    contentScale = ContentScale.Crop
                )
                hotel.rooms.forEach { room ->
                    RoomItem(room = room, onReserveClick = { onReserveClick(room, hotel) })
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RoomItem(room: Room, onReserveClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Room: ${room.room_type}, Price: ${room.price}€")
        room.images.forEach { image ->
            Image(
                painter = rememberAsyncImagePainter("http://13.39.162.212$image"),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(120.dp),
                contentScale = ContentScale.Crop
            )
        }
        Button(onClick = onReserveClick) {
            Text("Book this room")
        }
    }
}

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(dateString) ?: Date() // Si la data no és vàlida, fem servir la data actual
    return outputFormat.format(date)
}
