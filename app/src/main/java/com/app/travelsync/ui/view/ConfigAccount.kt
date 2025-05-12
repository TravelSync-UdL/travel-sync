package com.app.travelsync.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.ui.viewmodel.AuthViewModel
import com.app.travelsync.ui.viewmodel.ConfigAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigAccount(navController: NavController, viewModel: ConfigAccountViewModel = hiltViewModel(), authViewModel: AuthViewModel = hiltViewModel()) {
    // Obtenir les dades des del UserViewModel
    val userName by viewModel.userName.observeAsState("")
    val userSurname by viewModel.userSurname.observeAsState("")
    val userUsername by viewModel.userUsername.observeAsState("")

    var showSettingsMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.config_account_title)) },
                actions = {
                    Box {
                        IconButton(onClick = { showSettingsMenu = !showSettingsMenu }) {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings"
                            )
                        }
                        DropdownMenu(
                            expanded = showSettingsMenu,
                            onDismissRequest = { showSettingsMenu = false }
                        ) {
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "Version Icon"
                                    )
                                },
                                text = { Text(stringResource(id = R.string.about_text)) },
                                onClick = {
                                    showSettingsMenu = false
                                    navController.navigate("about")
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = "Settings Icon"
                                    )
                                },
                                text = { Text(stringResource(id = R.string.settings_text)) },
                                onClick = {
                                    showSettingsMenu = false
                                    navController.navigate("settings")
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Build,
                                        contentDescription = "Legal Icon"
                                    )
                                },
                                text = { Text(stringResource(id = R.string.legal_text)) },
                                onClick = {
                                    showSettingsMenu = false
                                    navController.navigate("legal")
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Logout,
                                        contentDescription = "Logout Icon"
                                    )
                                },
                                text = { Text("LogOut") },
                                onClick = {
                                    showSettingsMenu = false
                                    authViewModel.signout(navController)
                                    navController.navigate("login")
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de perfil (circular)
            Image(
                painter = painterResource(id = R.drawable.boat), // Substitueix amb la teva imatge de perfil
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nom d'usuari
            Text(
                text = stringResource(id = R.string.user_full_name_format, "$userName", "$userSurname"), // Nom complet de l'usuari
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Nom d'usuari (àlies)
            Text(
                text = stringResource(id = R.string.user_username_format, "@$userUsername"), // Nom d'usuari
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Nombre de seguidors
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "250", // Substitueix amb el número real de seguidors
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.followers),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "180", // Substitueix amb el número real de persones que segueixes
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.following),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Títol de les publicacions
            Text(
                text = stringResource(id = R.string.posts_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Llista de publicacions
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espai entre les publicacions
            ) {
                items(2) { index ->
                    PostItem(
                        imageResource = R.drawable.norway, // Substitueix per la imatge de la publicació
                        title = "Publicació ${index + 1}", // Títol de la publicació
                        description = "Aquesta és la descripció de la publicació ${index + 1}." // Descripció
                    )
                }
            }
        }
    }
}

@Composable
fun PostItem(imageResource: Int, title: String, description: String) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Imatge de la publicació
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Títol de la publicació
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descripció de la publicació
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}