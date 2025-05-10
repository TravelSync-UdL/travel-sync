package com.app.travelsync

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.travelsync.ui.view.AboutScreen
import com.app.travelsync.ui.view.ConfigAccount
import com.app.travelsync.ui.view.HomeScreen
import com.app.travelsync.ui.view.ItineraryScreen
import com.app.travelsync.ui.view.LegalScreen
import com.app.travelsync.ui.view.SearchScreen
import com.app.travelsync.ui.view.SettingsScreen
import com.app.travelsync.ui.view.TripScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.travelsync.ui.view.GalleryScreen
import com.app.travelsync.ui.view.LoginScreen
import com.app.travelsync.ui.view.RecoverPasswordScreen
import com.app.travelsync.ui.view.ReservationDetailScreen
import com.app.travelsync.ui.view.ReservationsScreen
import com.app.travelsync.ui.view.SignupScreen
import com.app.travelsync.ui.viewmodel.AuthState
import com.app.travelsync.ui.viewmodel.AuthViewModel
import com.app.travelsync.ui.viewmodel.HotelDetailScreen


@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph(navController: NavHostController, authViewModel: AuthViewModel = hiltViewModel()) {

    val authState by authViewModel.authState.observeAsState(AuthState.Unauthenticated)

    // Determinem quin ha de ser el startDestination
    val startDestination = if (authState is AuthState.Authenticated) "home" else "login"

    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentBackStackEntry?.destination?.route

    // Definim quines pantalles no han de mostrar la barra de navegació
    val hideBottomBar = currentDestination?.let {
        it.startsWith("itinerarys/") || it in listOf("about", "legal", "settings", "login", "recover", "signup")
    } == true

    Scaffold(
        bottomBar = {
            if (!hideBottomBar) { // Només es mostra si no estem a ItineraryScreen
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.CardTravel, contentDescription = "Trip") },
                        selected = currentDestination == "trip",
                        onClick = { navController.navigate("trip") },
                        label = { Text(stringResource(id = R.string.trip_nav)) }
                    )

                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ListAlt, contentDescription = "Reservations") },
                        selected = currentDestination == "guide",
                        onClick = { navController.navigate("guide") },
                        label = { Text(stringResource(id = R.string.reservation_nav)) }
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
                composable("trip") { TripScreen(navController, authViewModel = authViewModel) }
                composable("guide") { ReservationsScreen(navController) }
                composable("search") { SearchScreen(navController) }
                composable("you") { ConfigAccount(navController) }
                composable("about") { AboutScreen(navController) }
                composable("legal") { LegalScreen(navController) }
                composable("settings") { SettingsScreen(navController) }
                composable("login") { LoginScreen(navController) }
                composable("recover") { RecoverPasswordScreen(navController) }
                composable("signup") { SignupScreen(navController) }

                // Ruta on la barra de navegació NO es mostrarà
                composable(
                    route = "itinerarys/{taskId}",
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) {
                    ItineraryScreen(navController = navController)
                }

                composable("reservation_detail/{resId}") { backStackEntry ->
                    val resId = backStackEntry.arguments?.getString("resId")?.toIntOrNull()
                    resId?.let {
                        ReservationDetailScreen(reservationId = it, navController = navController)
                    }
                }

                composable("gallery/{tripId}") { backStackEntry ->
                    val tripId = backStackEntry.arguments?.getString("tripId")?.toIntOrNull() ?: return@composable
                    GalleryScreen(tripId = tripId, onBack = { navController.popBackStack() })
                }

                composable(
                    route = "hotel/{hotelId}/{groupId}/{start}/{end}",
                    arguments = listOf(
                        navArgument("hotelId") { type = NavType.StringType },
                        navArgument("groupId") { type = NavType.StringType },
                        navArgument("start")   { type = NavType.StringType },
                        navArgument("end")     { type = NavType.StringType }
                    )
                ) { back ->
                    HotelDetailScreen(
                        hotelId       = back.arguments!!.getString("hotelId")!!,
                        groupId       = back.arguments!!.getString("groupId")!!,
                        start         = back.arguments!!.getString("start")!!,
                        end           = back.arguments!!.getString("end")!!,
                        navController = navController
                    )
                }


            }
        }
    }
}
