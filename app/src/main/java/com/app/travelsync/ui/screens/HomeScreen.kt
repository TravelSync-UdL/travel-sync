package com.app.travelsync.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.travelsync.R

enum class NavMode{
    TRIP, GUIDE, HOME, SEARCH, YOU
}

@Composable
fun HomeScreen(navController: NavController){

    var selectedTravelSync by remember { mutableStateOf(NavMode.HOME) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Trip") },
                    selected = selectedTravelSync == NavMode.TRIP,
                    onClick = { selectedTravelSync = NavMode.TRIP },
                    label = { Text(stringResource(id=R.string.trip_nav)) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Guide") },
                    selected = selectedTravelSync == NavMode.GUIDE,
                    onClick = { selectedTravelSync = NavMode.GUIDE },
                    label = { Text(stringResource(id=R.string.guide_nav)) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = selectedTravelSync == NavMode.HOME,
                    onClick = { selectedTravelSync = NavMode.HOME },
                    label = { Text(stringResource(id=R.string.home_nav)) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    selected = selectedTravelSync == NavMode.SEARCH,
                    onClick = { selectedTravelSync = NavMode.SEARCH },
                    label = { Text(stringResource(id=R.string.search_nav)) }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "You") },
                    selected = selectedTravelSync == NavMode.YOU,
                    onClick = { selectedTravelSync = NavMode.YOU },
                    label = { Text(stringResource(id=R.string.configUser_nav)) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTravelSync) {
                NavMode.TRIP -> Trip()
                NavMode.GUIDE -> Guide()
                NavMode.HOME -> Home()
                NavMode.SEARCH -> Search()
                NavMode.YOU -> ConfigAccount()
            }

            if (selectedTravelSync == NavMode.HOME) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, end = 16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    MainUi(navController)
                }
            }
        }
    }
}

@Composable
fun MainUi(navController: NavController){
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        MiniFabItems(icon = ImageVector.vectorResource(id = R.drawable.help_foreground), title = stringResource(id = R.string.about_text), "About"),
        MiniFabItems(icon = ImageVector.vectorResource(id = R.drawable.legalicon_foreground), title = stringResource(id = R.string.legal_text), "Legal")
    )
    Column (horizontalAlignment = Alignment.End){

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically (initialOffsetY = {it}) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = {it}) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items.size){
                    ItemUi(icon = items[it].icon, title = items[it].title, navController, route = items[it].route)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat (label = "rotation"){
            if(it) 315f else 0f
        }

        FloatingActionButton(
            onClick = {expanded = !expanded},
            containerColor = colorResource(id = R.color.backgroundIcon)
        ) {
            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.settingicon_foreground), modifier = Modifier
                .rotate(rotation)
                .size(50.dp), contentDescription = "", tint = Color.Unspecified)
        }

    }
}

@Composable
fun ItemUi(icon: ImageVector, title: String, navController: NavController, route: String){
    val context = LocalContext.current
    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
        Spacer(modifier = Modifier.weight(1f))
        Box (

        ){
            Text(text = title)
        }
        Spacer(modifier = Modifier.width(10.dp))
        FloatingActionButton(onClick = {
            navController.navigate(route) {
                popUpTo("home") { inclusive = true }
            }
        }, modifier = Modifier.size(45.dp), containerColor = colorResource(id = R.color.backgroundIcon)) {
            Icon(imageVector = icon, contentDescription = "", tint = Color.Unspecified)
        }
    }
}

data class MiniFabItems(
    val icon: ImageVector,
    val title: String,
    val route: String
)

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

