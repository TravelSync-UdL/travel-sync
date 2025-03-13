package com.app.travelsync

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.travelsync.ui.view.AboutScreen
import com.app.travelsync.ui.view.ConfigAccount
import com.app.travelsync.ui.view.GuideScreen
import com.app.travelsync.ui.view.HomeScreen
import com.app.travelsync.ui.view.LegalScreen
import com.app.travelsync.ui.view.SearchScreen
import com.app.travelsync.ui.view.SettingsScreen
import com.app.travelsync.ui.view.TripScreen

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
        composable("Settings") { SettingsScreen(navController) }
    }
}