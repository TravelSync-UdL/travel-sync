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
fun SearchScreen(navController: NavController){

}

@Composable
fun Search() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SEARCH", style = MaterialTheme.typography.headlineMedium )


    }
}

/**
 * Cerca viatges guardats per l'usuari
 *
 * Rep els parametres: query
 */
fun searchTrips(){
    /*TODO*/
}

/**
 * Mostra destinacions recomanades relacionades amb la cerca
 *
 * Rep els parametres: query
 */
fun searchDestinations(){
    /*TODO*/
}

/**
 * Busca vols disponibles cap a la destinació desitjada
 *
 * Rep els parametres: query
 */
fun searchFlights(){
    /*TODO*/
}

/**
 * Mostra una llista d'hotels disponibles a la destinació seleccionada
 *
 * Rep els parametres: query
 */
fun searchHotels(){
    /*TODO*/
}

/**
 * Aplica filtres a la cerca
 *
 * Rep els parametres: filters
 */
fun filterSearchResults(){
    /*TODO*/
}