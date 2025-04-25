package com.app.travelsync

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.travelsync.ui.view.AboutScreen
import com.app.travelsync.ui.view.ConfigAccount
import com.app.travelsync.ui.view.GuideScreen
import com.app.travelsync.ui.view.HomeScreen
import com.app.travelsync.ui.view.ItineraryScreen
import com.app.travelsync.ui.view.LegalScreen
import com.app.travelsync.ui.view.SearchScreen
import com.app.travelsync.ui.view.SettingsScreen
import com.app.travelsync.ui.view.TripScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.travelsync.ui.view.LoginScreen
import com.app.travelsync.ui.view.RecoverPasswordScreen
import com.app.travelsync.ui.view.SignupScreen


@Composable
fun NavGraph(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination?.route

    val hideBottomBar = currentDestination?.let {
        it.startsWith("itinerarys/") || it in listOf("about", "legal", "settings", "login", "recover")
    } == true

    Scaffold(
        bottomBar = {
            if (!hideBottomBar) { // Només es mostra si no estem a ItineraryScreen
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Trip") },
                        selected = currentDestination == "trip",
                        onClick = { navController.navigate("trip") },
                        label = { Text(stringResource(id = R.string.trip_nav)) }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.LocationOn, contentDescription = "Guide") },
                        selected = currentDestination == "guide",
                        onClick = { navController.navigate("guide") },
                        label = { Text(stringResource(id = R.string.guide_nav)) }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        selected = currentDestination == "home",
                        onClick = { navController.navigate("home") },
                        label = { Text(stringResource(id = R.string.home_nav)) }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        selected = currentDestination == "search",
                        onClick = { navController.navigate("search") },
                        label = { Text(stringResource(id = R.string.search_nav)) }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "You") },
                        selected = currentDestination == "you",
                        onClick = { navController.navigate("you") },
                        label = { Text(stringResource(id = R.string.configUser_nav)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("trip") { TripScreen(navController) }
                composable("guide") { GuideScreen(navController) }
                composable("search") { SearchScreen(navController) }
                composable("you") { ConfigAccount(navController) }
                composable("about") { AboutScreen(navController) }
                composable("legal") { LegalScreen(navController) }
                composable("settings") { SettingsScreen(navController) }
                composable("login") { LoginScreen(navController) }
                composable("recover") { RecoverPasswordScreen(navController) }
                composable("signup") { SignupScreen(navController) }


                composable(
                    route = "itinerarys/{taskId}",
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) {
                    ItineraryScreen(navController = navController)
                }
            }
        }
    }
}
