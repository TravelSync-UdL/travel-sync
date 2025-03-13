package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.data.AppInfo

@Composable
fun AboutScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier.align(Alignment.TopStart).padding(top = 40.dp, end = 16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back_button_description))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Títol de la pàgina
            Text(
                text = stringResource(id = R.string.about_screen_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center  // Centrat del títol
            )

            // Resum de l'aplicació
            Text(
                text = stringResource(id = R.string.about_screen_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify  // Justificat
            )

            // Descripció addicional
            Text(
                text = stringResource(id = R.string.about_screen_additional_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 64.dp),
                textAlign = TextAlign.Justify  // Justificat
            )

            // Informació sobre l'equip
            Text(
                text = stringResource(id = R.string.about_screen_development_team),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify
            )

            // Versió de l'aplicació
            Text(
                text = stringResource(id = R.string.about_screen_version) + "${AppInfo.versionName} (${AppInfo.versionCode})",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
}