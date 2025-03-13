package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.app.travelsync.R

@Composable
fun LegalScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botó de retrocedir
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp)
                .zIndex(1f)  // Asegurem que el botó estigui per sobre d'altres elements
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back_button_description))
        }

        // Column amb desplaçament
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Habilitar desplaçament
        ) {
            // Afegir espai abans del títol
            Spacer(modifier = Modifier.height(80.dp)) // Afegeix espai abans del títol per situar-lo més avall

            // Títol de l'aplicació
            Text(
                text = stringResource(id = R.string.legal_screen_title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            // Última actualització
            Text(
                text = stringResource(id = R.string.legal_screen_last_update),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            // Descripció d'ús de l'aplicació
            Text(
                text = stringResource(id = R.string.legal_screen_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            // Seccions dels Termes amb estil
            TermSection(
                title = stringResource(id = R.string.legal_section_1),
                content = stringResource(id = R.string.legal_section_1_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_2),
                content = stringResource(id = R.string.legal_section_2_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_3),
                content = stringResource(id = R.string.legal_section_3_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_4),
                content = stringResource(id = R.string.legal_section_4_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_5),
                content = stringResource(id = R.string.legal_section_5_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_6),
                content = stringResource(id = R.string.legal_section_6_content)
            )

            TermSection(
                title = stringResource(id = R.string.legal_section_7),
                content = stringResource(id = R.string.legal_section_7_content)
            )
        }
    }
}

// Funció per a cada secció
@Composable
fun TermSection(title: String, content: String) {
    Column(modifier = Modifier.padding(bottom = 32.dp)) {
        // Títol de la secció
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Left
        )

        // Contingut de la secció
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Left
        )
    }
}
