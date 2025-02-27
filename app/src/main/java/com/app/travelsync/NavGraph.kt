package com.app.travelsync

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.travelsync.ui.screens.GuideScreen
import com.app.travelsync.ui.screens.HomeScreen
import com.app.travelsync.ui.screens.SearchScreen
import com.app.travelsync.ui.screens.TripScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("trip") { TripScreen(navController) }
        composable("guide") { GuideScreen(navController) }
        composable("search") { SearchScreen(navController) }
    }
}