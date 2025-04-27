package com.app.travelsync.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.ui.viewmodel.AuthViewModel

@Composable
fun RecoverPasswordScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.recov_pass),
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(stringResource(id = R.string.email_label)) }
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.sendPasswordResetEmail(email.toString())
            Toast.makeText(context, context.getString(R.string.email_exist), Toast.LENGTH_LONG).show()
            navController.navigate("login")
        }) {
            Text(text = stringResource(id = R.string.send_email))
        }
    }
}
