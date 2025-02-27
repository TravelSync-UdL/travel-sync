package com.app.travelsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

enum class NavMode{
    TRIP, GUIDE, HOME, SEARCH
}

@Composable
fun HomeScreen(navController: NavController){

    var selectedCalculator by remember { mutableStateOf(NavMode.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Trip") },
                    selected = selectedCalculator == NavMode.TRIP,
                    onClick = { selectedCalculator = NavMode.TRIP },
                    label = { Text("Trip") }
                )
                
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Guide") },
                    selected = selectedCalculator == NavMode.GUIDE,
                    onClick = { selectedCalculator = NavMode.GUIDE },
                    label = { Text("Guide") }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = selectedCalculator == NavMode.HOME,
                    onClick = { selectedCalculator = NavMode.HOME },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    selected = selectedCalculator == NavMode.SEARCH,
                    onClick = { selectedCalculator = NavMode.SEARCH },
                    label = { Text("Search") }
                )
            }
        }
    ) { innerPadding ->
        // Contenido que se actualiza según el modo seleccionado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedCalculator) {
                NavMode.TRIP -> Trip()
                NavMode.GUIDE -> Guide()
                NavMode.HOME -> Home()
                NavMode.SEARCH -> Search()
            }
        }
    }
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

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "HOME", style = MaterialTheme.typography.headlineMedium )


    }
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
