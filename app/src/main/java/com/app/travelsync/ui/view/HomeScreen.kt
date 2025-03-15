package com.app.travelsync.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.travelsync.R // Importa el teu package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var showSettingsMenu by remember { mutableStateOf(false) }

    // Ensure that rememberPosts() is called inside the composable context
    val posts = rememberPosts()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
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
                                        imageVector = Icons.Filled.Build,
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
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "Legal Icon"
                                    )
                                },
                                text = { Text(stringResource(id = R.string.legal_text)) },
                                onClick = {
                                    showSettingsMenu = false
                                    navController.navigate("legal")
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espai entre cada publicació
            ) {
                items(posts.size) { index ->
                    var liked by remember { mutableStateOf(false) }

                    // Cada publicació té el seu propi estat de "liked" i la seva pròpia imatge
                    PostItem(
                        imageResource = posts[index].imageResource, // Usar la imatge associada amb cada publicació
                        title = posts[index].title,
                        description = posts[index].description,
                        liked = liked,
                        onLikeClick = { liked = !liked }
                    )
                }
            }
        }
    }
}

data class PostData(val title: String, val description: String, val imageResource: Int) // Afegim la propietat imageResource

@Composable
fun rememberPosts(): List<PostData> {
    return listOf(
        PostData(
            title = stringResource(id = R.string.post_1_title),
            description = stringResource(id = R.string.post_1_desc),
            imageResource = R.drawable.boat
        ),
        PostData(
            title = stringResource(id = R.string.post_2_title),
            description = stringResource(id = R.string.post_2_desc),
            imageResource = R.drawable.castle
        ),
        PostData(
            title = stringResource(id = R.string.post_3_title),
            description = stringResource(id = R.string.post_3_desc),
            imageResource = R.drawable.norway
        )
    )
}

@Composable
fun PostItem(
    imageResource: Int,
    title: String,
    description: String,
    liked: Boolean,
    onLikeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Imatge de la publicació utilitzant una imatge local de 'drawable'
        Image(
            painter = painterResource(id = imageResource), // Carrega la imatge local
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Títol de la publicació amb més padding a l'esquerra per moure el text a la dreta
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp) // Moure més cap a la dreta
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descripció amb més padding a l'esquerra per moure el text a la dreta
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp) // Moure més cap a la dreta
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botó per marcar "M'agrada"
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = if (liked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = if (liked) "Liked" else "Like"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
