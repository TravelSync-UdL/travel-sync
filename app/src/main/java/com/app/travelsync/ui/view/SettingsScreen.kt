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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val language = viewModel.language

    var tempName by remember { mutableStateOf(viewModel.name) }
    var tempSurname by remember { mutableStateOf(viewModel.surname) }
    var tempUsername by remember { mutableStateOf(viewModel.username) }
    var tempEmail by remember { mutableStateOf(viewModel.email) }
    var tempPassword by remember { mutableStateOf(viewModel.password) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back_button_description))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
            Text(stringResource(id = R.string.settings_screen_title), style = MaterialTheme.typography.headlineLarge)

            // Secció "Perfil"
            Text(stringResource(id = R.string.profile_section_title), style = MaterialTheme.typography.headlineMedium)
            OutlinedTextField(
                value = tempName,
                onValueChange = { tempName = it },
                label = { Text(stringResource(id = R.string.name_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tempSurname,
                onValueChange = { tempSurname = it },
                label = { Text(stringResource(id = R.string.surname_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Secció "Compte"
            Text(stringResource(id = R.string.account_section_title), style = MaterialTheme.typography.headlineMedium)
            OutlinedTextField(
                value = tempUsername,
                onValueChange = { tempUsername = it },
                label = { Text(stringResource(id = R.string.username_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tempEmail,
                onValueChange = { tempEmail = it },
                label = { Text(stringResource(id = R.string.email_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tempPassword,
                onValueChange = { tempPassword = it },
                label = { Text(stringResource(id = R.string.password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Secció "Idioma"
            Text(stringResource(id = R.string.language_section_title), style = MaterialTheme.typography.headlineMedium)
            LanguageDropdown(
                selectedLanguage = language,
                onLanguageSelected = { newLang -> viewModel.updateLanguage(newLang) },
                availableLanguages = listOf("en", "es", "ca")
            )

            // Botó per guardar la configuració
            Button(
                onClick = {
                    viewModel.saveSettings(tempName, tempSurname, tempUsername, tempEmail, tempPassword)

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Dades editades correctament")
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("OK")
            }
        }
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
        "es" -> stringResource(id = R.string.language_spanish)
        "en" -> stringResource(id = R.string.language_english)
        "ca" -> stringResource(id = R.string.language_catalan)
        else -> selectedLanguage
    }

    OutlinedTextField(
        value = languageDisplay,
        onValueChange = {},
        label = { Text(stringResource(id = R.string.language_label)) },
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
                "es" -> stringResource(id = R.string.language_spanish)
                "en" -> stringResource(id = R.string.language_english)
                "ca" -> stringResource(id = R.string.language_catalan)
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
