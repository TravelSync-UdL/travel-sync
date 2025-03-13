package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TripScreen (navController: NavController){

}

@Composable
fun Trip() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "TRIP", style = MaterialTheme.typography.headlineMedium )


    }
}

/**
 * Carrega tota la informacio d'un viatge guardat
 *
 * Rep els parametres: tripId
 */
fun loadTripDetails() {


}

/**
 * Permet modificar la informacio del viatge
 *
 * Rep els parametres: tripId, newInfo
 */
fun editTrip() {


}

/**
 *  Elimina un viatge guardat
 *
 * Rep els parametres: tripId
 */
fun deleteTrip() {


}

/**
 *  Afegeix una activitat del viatge
 *
 * Rep els parametres: tripId, activity
 */
fun addActivityToTrip(){


}

/**
 *  Elimina una activitat del viatge
 *
 * Rep els parametres: tripId, activity
 */
fun removeActivityFromTrip(){

}

/**
 *  Mostra el mapa de totes les activitats i els llocs programats del viatge
 *
 * Rep els parametres: tripId
 */
fun viewTripMap(){

}
