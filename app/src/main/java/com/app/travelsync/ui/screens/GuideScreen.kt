package com.app.travelsync.ui.screens

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
fun GuideScreen(navController: NavController){

}

@Composable
fun Guide() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "GUIDE", style = MaterialTheme.typography.headlineMedium )


    }
}

/**
 * Retorna una llsiat de llocs turistics populars en la direcció seleccionada
 *
 * Rep els parameteres: destination
 */
fun fetchLocalAttractions(){

}

/**
 * Mostra una llista de restaurants recomanats amb detalls com valoracions i tipus de menjar
 *
 * Rep els parametres: destination
 */
fun fetchRestaurants(){

}

/**
 * Proporciona tota la informacio sobre els mitjants de trasnsport
 *
 * Rep els parametres: destination
 */
fun fetchTransportOptions(){

}

/**
 * Mostra la previsio meteorologica per als dies a la destinacio
 *
 * Rep els parametres: destination
 */
fun getWeatherForecast(){

}