package com.app.travelsync

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.travelsync.ui.screens.AboutScreen
import com.app.travelsync.ui.screens.ConfigAccount
import com.app.travelsync.ui.screens.GuideScreen
import com.app.travelsync.ui.screens.HomeScreen
import com.app.travelsync.ui.screens.LegalScreen
import com.app.travelsync.ui.screens.SearchScreen
import com.app.travelsync.ui.screens.TripScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("trip") { TripScreen(navController) }
        composable("guide") { GuideScreen(navController) }
        composable("search") { SearchScreen(navController) }
        composable("you") { ConfigAccount(navController) }
        composable("About") { AboutScreen(navController) }
        composable("Legal") { LegalScreen(navController) }
    }
}