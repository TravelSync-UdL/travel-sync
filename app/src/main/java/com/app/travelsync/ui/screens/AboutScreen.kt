package com.app.travelsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutScreen(navController: NavController){
    Box (modifier = Modifier.fillMaxSize()){

        IconButton(
            onClick = {navController.navigate("home")},
            modifier = Modifier.align(Alignment.TopStart).padding(top = 40.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Títol de la pàgina
            Text(
                text = "Sobre TravelSync",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center  // Centrat del títol
            )

            // Resum de l'aplicació
            Text(
                text = "TravelSync és una aplicació de planificació de viatges dissenyada per ajudar-te a crear itineraris personalitzats. Organitza el teu viatge, descobreix noves destinacions i comparteix els teus plans amb altres usuaris.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify  // Justificat
            )

            // Descripció addicional
            Text(
                text = "TravelSync ha estat creat per un equip de desenvolupadors passió per la tecnologia i els viatges. El nostre objectiu és ajudar-te a organitzar els teus viatges de manera fàcil i eficient.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 64.dp),
                textAlign = TextAlign.Justify  // Justificat
            )

            // Informació sobre l'equip
            Text(
                text = "Equip de Desenvolupament: Gerard Moliner i Condomines",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify
            )

            // Versió de l'aplicació
            Text(
                text = "Versió: 1.1.0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify
            )

        } }
}