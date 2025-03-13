package com.app.travelsync.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.ui.viewmodel.SettingsViewModel

import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // Accedim a les propietats directament
    val language = viewModel.language

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back_button_description))
                    }
                }
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Configuració de l'aplicació",
                style = MaterialTheme.typography.headlineLarge
            )

            // Secció "Perfil"
            Text(
                "Perfil",
                style = MaterialTheme.typography.headlineMedium
            )
            ProfileSection()

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Secció "Compte"
            Text(
                "Compte",
                style = MaterialTheme.typography.headlineMedium
            )
            AccountSection()

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Botó per guardar la configuració
            Button(
                onClick = { /* Aquí pots afegir la lògica per guardar els canvis */ },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("OK")
            }

            // Secció "Idioma"
            Text(
                "Idioma",
                style = MaterialTheme.typography.headlineMedium
            )
            LanguageDropdown(
                selectedLanguage = language,
                onLanguageSelected = { newLang -> viewModel.updateLanguage(newLang) },
                availableLanguages = listOf("en", "es", "ca")
            )
        }
    }
}

@Composable
fun ProfileSection() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Nom
        OutlinedTextField(
            value = "",  // El valor està buit, no hi ha gestió d'estat
            onValueChange = { }, // Aquí pots gestionar el canvi més endavant
            label = { Text("Nom") },
            modifier = Modifier.fillMaxWidth()
        )
        // Cognom
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Cognom") },
            modifier = Modifier.fillMaxWidth()
        )
        // Ciutat
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Ciutat") },
            modifier = Modifier.fillMaxWidth()
        )
        // Descripció com a camp multilinea
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Descripció") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5, // Permet múltiples línies
            minLines = 3  // Permet que el camp tingui almenys 3 línies visibles
        )
    }
}

@Composable
fun AccountSection() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Nom d'usuari
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Nom d'usuari") },
            modifier = Modifier.fillMaxWidth()
        )
        // Correu electrònic
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Correu electrònic") },
            modifier = Modifier.fillMaxWidth()
        )
        // Contrasenya
        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val languageDisplay = when (selectedLanguage) {
        "es" -> "Español"
        "en" -> "English"
        "ca" -> "Catalan"
        else -> selectedLanguage
    }

    OutlinedTextField(
        value = languageDisplay,
        onValueChange = {},
        label = { Text("Idioma") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Mostrar idiomas")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        availableLanguages.forEach { lang ->
            val langName = when (lang) {
                "es" -> "Español"
                "en" -> "English"
                "ca" -> "Catalan"
                else -> lang
            }
            DropdownMenuItem(
                text = { Text(langName) },
                onClick = {
                    onLanguageSelected(lang)
                    expanded = false
                }
            )
        }
    }
}
