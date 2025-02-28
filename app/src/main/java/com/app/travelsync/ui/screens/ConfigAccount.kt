package com.app.travelsync.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfigAccount(navController: NavController){

}

@Composable
fun ConfigAccount() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Config Account", style = MaterialTheme.typography.headlineMedium )


    }
}

/**
 * Permet editar el nom, foto de perfil i altres dades de l'usuari
 *
 * Rep els parametres: userId, newInfo
 */
fun updateUserProfile(){

}

/**
 * Modifica la contrasenya de l'usuar despres de verificar la contrasenya
 *
 * Rep els parametres:userId, oldPassword, newPassword
 */
fun changePassword(){

}

/**
 * Configura preferencies personals com idioma, moneda o tipus de destinacio perfereida
 *
 * Rep els parametres: userId, preference
 */
fun setPreferences(){

}

/**
 * Tanca la sessio de l'usuari i retorna a la pagina de login
 *
 */
fun logout(){

}

/**
 *Permet a l'usuari deleccionar l'idioma de l'app entre les difernets opcions
 *
 * Rep els parametres:userId, languageCode
 */
fun changeLanguage(){

}