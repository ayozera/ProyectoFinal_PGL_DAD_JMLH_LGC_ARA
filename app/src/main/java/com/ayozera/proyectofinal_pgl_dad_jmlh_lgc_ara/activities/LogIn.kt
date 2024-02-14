package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@Composable
fun LogIn(navController: NavHostController, appMainViewModel: AppMainViewModel) {
    //val logInViewModel = remember { LogInViewModel() }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        LogInHeader(Modifier.fillMaxHeight(0.1f))
        LogInBody(navController,Modifier.fillMaxHeight(0.8f))
        ChangeToSignUp(navController,Modifier.fillMaxHeight(0.8f))
    }

}


@Composable
fun LogInHeader(weight: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = weight
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    ) {
        Text(
            text = "Turns & points",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 25.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.dados),
            contentDescription = "Logo Turns & Points",
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}

@Composable
fun LogInBody(navController: NavHostController, weight: Modifier) {
    var textUser by remember { mutableStateOf("") }
    var textPass by remember { mutableStateOf("") }
    var userIsCorrect by remember { mutableStateOf(true) }
    var passIsCorrect by remember { mutableStateOf(true) }
    //val credentials = DataUp.loadCredentials(LocalContext.current)

    Column(
        modifier = weight
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.padding(0.dp,10.dp,0.dp,15.dp)
        )
        Text(
            text = "Escribe tu nombre de usuario",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = userIsCorrect)
        )
        TextField(
            value = textUser,
            placeholder = { Text("Usuario",
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
            text = "Escibe tu contraseña",
            fontSize = 20.sp,
            color = setTextFieldColor(isCorrect = passIsCorrect)
        )
        TextField(
            value = textPass,
            placeholder = { Text("Contraseña",
                fontSize = 24.sp) },
            onValueChange = { textPass = it
                passIsCorrect = textPass.isNotBlank()
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
        TextButton(
            onClick = {
                passIsCorrect = textPass.isNotBlank()
                userIsCorrect = textUser.isNotBlank()
                if (passIsCorrect && userIsCorrect) {
                        navController.navigate("home")
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
                text = "Entrar",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}
@Composable
fun ChangeToSignUp(navController: NavHostController, weight: Modifier) {
    Column (modifier = weight
        .fillMaxWidth()
        .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "¿No tienes cuenta? Registrate!",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(10.dp)
        )
        TextButton(
            onClick = {
                navController.navigate("signUp")
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
