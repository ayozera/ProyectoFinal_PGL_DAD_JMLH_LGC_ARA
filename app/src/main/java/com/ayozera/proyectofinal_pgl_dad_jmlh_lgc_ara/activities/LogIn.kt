package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R

@Composable
fun LogIn(navController: NavHostController) {

    var textUser by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    // val keys = DataUp.credentialLoader(LocalContext.current)
    var openDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        ) {
            Text(
                text = "Turn & points",
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 25.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.dados),
                contentDescription = "Logo de dados",
                modifier = Modifier.padding(start = 25.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Inicia sesión", fontSize = 24.sp)
            Text(text = "Introduzca nombre de usuario")
            TextField(
                value = textUser,
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(10.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Introduzca su contraseña")
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(50.dp))
            Button(onClick = { }) {
                Text(text = "Iniciar Sesión")
            }
        }

        if (openDialog) {
            AccessDialogError {
                openDialog = false
            }
        }
    }
}



@Composable
fun AccessDialogError(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text("Asegúrese de que el usuario y la contraseña son correctas")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismissClick()
                        }) {
                        Text("Entendido")
                    }
                },
            )
        }
    }
}


