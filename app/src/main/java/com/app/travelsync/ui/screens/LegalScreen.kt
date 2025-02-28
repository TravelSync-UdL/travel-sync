package com.app.travelsync.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@Composable
fun LegalScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Botó de retrocedir
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp) // Ajustat per garantir que no estigui cobert
                .zIndex(1f)  // Asegurem que el botó estigui per sobre d'altres elements
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
                text = "\uD83D\uDCDC Termes i Condicions - TravelSync",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            // Última actualització
            Text(
                text = "Última actualització: 28/02/2025",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            // Descripció d'ús de l'aplicació
            Text(
                text = "Benvingut a TravelSync! Abans d’utilitzar la nostra aplicació, si us plau, llegeix atentament aquests Termes i Condicions. En utilitzar l’aplicació, acceptes aquestes condicions. Si no hi estàs d’acord, si us plau, no utilitzis l’aplicació",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center
            )

            // Seccions dels Termes amb estil
            TermSection(
                title = "1\uFE0F⃣ Ús de l’Aplicació",
                content = """
                - TravelSync és una eina de planificació de viatges que ajuda els usuaris a organitzar itineraris, compartir plans i gestionar reserves.
                - Has de tenir almenys 16 anys per utilitzar aquesta aplicació.
                - No pots utilitzar l’aplicació per activitats il·legals o fraudulentes.
                """
            )

            TermSection(
                title = "2\uFE0F⃣ Registre i Seguretat",
                content = """
                - Pots registrar-te mitjançant correu electrònic o comptes socials.
                - L’usuari és responsable de mantenir segures les seves credencials d’accés.
                - En cas d’accés no autoritzat al teu compte, has de notificar-nos immediatament.
                """
            )

            TermSection(
                title = "3\uFE0F⃣ Drets i Responsabilitats de l'Usuari",
                content = """
                - Ets responsable de la informació que introdueixes a TravelSync.
                - No pots compartir informació falsa, enganyosa o ofensiva.
                - No està permès l’ús de bots o programari automatitzat per accedir a l’aplicació.
                """
            )

            TermSection(
                title = "4\uFE0F⃣ Privacitat i Protecció de Dades",
                content = """
                - Recopilem informació com destinacions, preferències de viatge i reserves per millorar la teva experiència.
                - No compartim les teves dades amb tercers sense el teu consentiment. Consulta la Política de Privacitat per a més informació.
                """
            )

            TermSection(
                title = "5\uFE0F⃣ Limitació de Responsabilitat",
                content = """
                - TravelSync no es fa responsable de cancel·lacions de viatges, errors en reserves o problemes amb proveïdors externs.
                - La informació proporcionada pot no ser sempre exacta o actualitzada.
                """
            )

            TermSection(
                title = "6\uFE0F⃣ Modificacions i Finalització del Servei",
                content = """
                - Ens reservem el dret de modificar o suspendre l’aplicació en qualsevol moment.
                - Podem modificar aquests termes i notificarem als usuaris sobre els canvis.
                """
            )

            TermSection(
                title = "7\uFE0F⃣ Contacte",
                content = """
                Per qualsevol dubte o consulta, pots contactar amb nosaltres a support@travelsync.com.
                """
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
