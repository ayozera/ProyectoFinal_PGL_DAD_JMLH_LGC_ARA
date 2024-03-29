package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.SignUpViewModel

@Composable
fun SignUp(navController: NavHostController) {
    val signUpViewModel = remember { SignUpViewModel() }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        LogInHeader(Modifier.fillMaxHeight(0.1f))
        SingUpBody(signUpViewModel,navController,Modifier.fillMaxHeight(0.8f))
        ChangeToLogIn(navController,Modifier.fillMaxHeight(0.8f))
    }

}
@Composable
fun SingUpBody(
    viewModel: SignUpViewModel,
    navController: NavHostController,
    modifier: Modifier
) {
    var textUser by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textPass by remember { mutableStateOf("") }
    var textPassRepeat by remember { mutableStateOf("") }
    val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
    var userIsCorrect by remember { mutableStateOf(true) }
    var emailIsCorrect by remember { mutableStateOf(true) }
    val passIsCorrect by remember { mutableStateOf(true) }
    var passRIsCorrect by remember { mutableStateOf(true) }
    // val keys = DataUp.credentialLoader(LocalContext.current)

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(0.dp,10.dp,0.dp,15.dp)
        )
        Text(
            text = "Elige un nombre de usuario",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = userIsCorrect)
        )
        TextField(
            value = textUser,
            placeholder = { Text("Nombre de usuario",
                fontSize = 24.sp) },
            onValueChange = { textUser = it
                            userIsCorrect = textUser.isNotBlank()
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    setTextFieldColor(isCorrect = userIsCorrect),
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Escribe tu correo electrónico",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = emailIsCorrect)
        )
        TextField(
            value = textEmail,
            placeholder = { Text("email@ejemplo.com",
                fontSize = 24.sp) },
            onValueChange = { textEmail = it
                            emailIsCorrect = emailRegex.matches(textEmail)},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    setTextFieldColor(isCorrect = emailIsCorrect),
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Elige una contraseña",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = passIsCorrect)
        )
        TextField(
            value = textPass,
            placeholder = { Text("Contraseña",
                fontSize = 24.sp) },
            onValueChange = { textPass = it
                            textPass.length >= 8
            },
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    setTextFieldColor(isCorrect = passIsCorrect),
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Repite tu contraseña",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = passRIsCorrect)
        )
        TextField(
            value = textPassRepeat,
            placeholder = { Text("Contraseña",
                fontSize = 24.sp) },
            onValueChange = { textPassRepeat = it
                            passRIsCorrect = textPass == textPassRepeat},
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    setTextFieldColor(isCorrect = passRIsCorrect),
                    RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextButton(
            onClick = {
                if (textPass.length < 8 || textPass != textPassRepeat ) {
                    Toast.makeText(navController.context, "Contraseña menor a 8 caracteres o incorrecta", Toast.LENGTH_LONG).show()
                }else if (!emailRegex.matches(textEmail)) {
                        Toast.makeText(navController.context, "Email con formato incorrecto", Toast.LENGTH_SHORT).show()
                }else if (textUser.isBlank()) {
                    Toast.makeText(navController.context, "Usuario no válido", Toast.LENGTH_SHORT).show()
                }else {
                viewModel.createAccount(
                    textUser.trim(),
                    textEmail.trim(),
                    textPass,
                    onSuccess = {
                        //navController.navigate(Routs.Profile.rout)
                        Toast.makeText(navController.context, "Usuario creado", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { error ->
                        Toast.makeText(navController.context, error, Toast.LENGTH_SHORT).show()
                    }
                )
                }
            },
            modifier = Modifier
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.shapes.extraLarge
                )
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.primary),

            ) {
            Text(
                text = "Registrarse",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}
@Composable
fun ChangeToLogIn(navController: NavHostController, modifier: Modifier) {
    Column (modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "¿Ya tienes cuenta? Inicia Sesión!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(10.dp)
        )
        TextButton(
            onClick = {
                navController.navigate("logIn")
            },
            modifier = Modifier
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.shapes.extraLarge
                )
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.primary),

            ) {
            Text(
                text = "Iniciar Sesión",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}

@Composable
fun setTextFieldColor(isCorrect: Boolean): Color {
    return if (isCorrect) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }
}