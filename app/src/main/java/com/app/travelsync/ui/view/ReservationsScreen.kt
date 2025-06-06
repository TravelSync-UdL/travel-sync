package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.ui.components.ReservationRow
import com.app.travelsync.ui.viewmodel.ReservationsViewModel


@Composable
fun ReservationsScreen(navController: NavController, vm: ReservationsViewModel = hiltViewModel()) {
    val ui = vm.uiState.collectAsState()

    LaunchedEffect(Unit) { vm.load() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)

    ) {


        item {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {


                Text(
                    text = stringResource(R.string.my_reservations),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.statusBarsPadding().padding(start = 16.dp, bottom = 12.dp)
                )


                IconButton(onClick = { vm.load() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload"
                    )
                }

            }
        }

        items(ui.value.reservations) { r ->
            ReservationRow(
                res = r,
                onCancel = { vm.cancel(r) }
            )
        }
    }
}