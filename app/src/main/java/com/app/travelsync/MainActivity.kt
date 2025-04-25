package com.app.travelsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.app.travelsync.ui.theme.TravelSyncTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var isChecking = true

        lifecycleScope.launch {
            delay(100L)
            isChecking = false
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isChecking
            }
        }
        enableEdgeToEdge()

        setContent {
            TravelSyncTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val isUserLoggedIn = Firebase.auth.currentUser != null

    androidx.compose.runtime.LaunchedEffect(Unit) {
        if (isUserLoggedIn) {
            navController.navigate("home") {
                popUpTo(0)
            }
        } else {
            navController.navigate("login") {
                popUpTo(0)
            }
        }
    }

    NavGraph(navController = navController)
}