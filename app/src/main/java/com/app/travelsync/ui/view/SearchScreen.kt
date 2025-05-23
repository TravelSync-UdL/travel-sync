package com.app.travelsync.ui.view

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.app.travelsync.R
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Room
import com.app.travelsync.ui.viewmodel.SearchViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {

    object Hotel    : Screen("hotel/{hotelId}/{groupId}/{start}/{end}", Icons.Default.Hotel, "Hotel") {
        fun create(hid: String, gid: String, s: String, e: String) = "hotel/$hid/$gid/$s/$e"
    }

}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, vm: SearchViewModel = hiltViewModel()) {

    val ui by vm.uiState.collectAsState()

    Column(Modifier.padding(16.dp)) {

        ExposedDropdownMenuBox(
            expanded = ui.cityMenu,
            onExpandedChange = { vm.toggleCityMenu() }
        ) {
            TextField(
                value = ui.city,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.city_label)) },
                leadingIcon = { Icon(Icons.Default.Place, null) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = ui.cityMenu,
                onDismissRequest = { vm.toggleCityMenu() }
            ) {
                listOf(
                    stringResource(R.string.city_barcelona),
                    stringResource(R.string.city_paris),
                    stringResource(R.string.city_london)
                ).forEach { c ->
                    DropdownMenuItem(
                        text = { Text(c) },
                        onClick = { vm.selectCity(c) }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        DateField(
            label = stringResource(R.string.start_label),
            date = ui.startDate,
            onPick = { vm.pickStart(it) }
        )
        Spacer(Modifier.height(8.dp))
        DateField(
            label = stringResource(R.string.end_label),
            date = ui.endDate,
            onPick = { vm.pickEnd(it) },
            minDateMillis = ui.startDate?.plusDays(1)?.let {
                GregorianCalendar.from(it.atStartOfDay(ZoneId.systemDefault())).timeInMillis
            }
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.search() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.backgroundIcon),
                contentColor = Color.White
            )
        ) { Text(stringResource(R.string.search_button)) }

        Spacer(Modifier.height(16.dp))

        if (ui.loading) {
            CircularProgressIndicator()
        } else {
            HotelList(ui.hotels) { h ->
                navController.navigate(
                    Screen.Hotel.create(
                        h.id,
                        vm.groupId,
                        ui.startDate.toString(),
                        ui.endDate.toString()
                    )
                )
            }

            if (ui.message != null) {
                Text(
                    text = ui.message!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onPick: (LocalDate) -> Unit,
    minDateMillis: Long? = null
) {
    val context = LocalContext.current
    val formatter = DateTimeFormatter.ISO_DATE

    OutlinedTextField(
        value = date?.format(formatter) ?: "",
        onValueChange = {},
        readOnly = true,
        enabled = false,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val now = date ?: LocalDate.now()
                val datePicker = DatePickerDialog(
                    context,
                    { _, y, m, d ->
                        onPick(LocalDate.of(y, m + 1, d))
                    },
                    now.year, now.monthValue - 1, now.dayOfMonth
                )
                datePicker.datePicker.minDate = minDateMillis ?: Calendar.getInstance().timeInMillis
                datePicker.show()
            }
    )
}


@Composable
fun HotelList(hotels: List<Hotel>, onClick: (Hotel) -> Unit) {

    LazyColumn {
        items(hotels) { h ->
            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { onClick(h) }) {
                Log.d("home", h.id)
                val id = h.id
                Row(Modifier.height(120.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter("http://13.39.162.212" + ( h.imageUrl ?: "")),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.width(120.dp).fillMaxHeight()
                    )
                    Column(Modifier.padding(8.dp)) {
                        Text(h.name + " ($id)", fontWeight = FontWeight.Bold)
                        Text(h.address)
                        Spacer(Modifier.weight(1f))
                        Text(
                            stringResource(R.string.from_price_format, h.rooms?.minOfOrNull { it.price } ?: "-"),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }


}
