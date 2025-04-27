package com.app.travelsync.ui.view

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.travelsync.R
import com.app.travelsync.data.local.entity.UserEntity
import com.app.travelsync.ui.viewmodel.AuthState
import com.app.travelsync.ui.viewmodel.AuthViewModel
import java.util.*
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var acceptReceiveEmails by remember { mutableStateOf(true) }

    val calendar = Calendar.getInstance()
    val context = LocalContext.current

    val authState = authViewModel.authState.observeAsState()

    val openDatePicker: () -> Unit = {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                birthdate = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                Toast.makeText(context, context.getString(R.string.account_ok), Toast.LENGTH_LONG).show()
                navController.navigate("login")
            }
            is AuthState.Error -> {
                val error = (authState.value as AuthState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.creat_account), fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.name_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text(stringResource(R.string.surname_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.username_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.email_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.password_label)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { openDatePicker() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (birthdate.isNotEmpty())
                    "${stringResource(R.string.bithdate)} $birthdate"
                else
                    stringResource(R.string.no_birthdate)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text(stringResource(R.string.country)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text(stringResource(R.string.phone)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = acceptReceiveEmails,
                onCheckedChange = { acceptReceiveEmails = it }
            )
            Text(stringResource(R.string.accept_email))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && surname.isNotBlank() && username.isNotBlank()) {
                    val userEntity = UserEntity(
                        login = email,
                        username = username,
                        country = country,
                        birthdate = birthdate,
                        address = "undefined",
                        phone = phone,
                        acceptReceiveEmails = acceptReceiveEmails
                    )
                    authViewModel.signup(email, password, userEntity)
                } else {
                    Toast.makeText(context, context.getString(R.string.fill_fields), Toast.LENGTH_LONG).show()
                }
            },
            enabled = authState.value != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.register))
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(stringResource(R.string.login_log))
        }
    }
}
