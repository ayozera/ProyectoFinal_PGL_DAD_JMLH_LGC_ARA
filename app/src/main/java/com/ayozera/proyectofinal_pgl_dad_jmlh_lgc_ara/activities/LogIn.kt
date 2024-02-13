package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.SignUpViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.logInViewModel

@Composable
fun LogIn(navController: NavHostController) {
    val logInViewModel = remember { logInViewModel() }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        LogInHeader()
        LogInBody(logInViewModel, navController)
    }

}

@Composable
fun LogInHeader() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
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
}

@Composable
fun LogInBody(viewModel: logInViewModel, navController: NavHostController) {
    var textUser by remember { mutableStateOf("") }
    var textPass by remember { mutableStateOf("") }
    var userIsCorrect by remember { mutableStateOf(true) }
    var passIsCorrect by remember { mutableStateOf(true) }
    val credentials = DataUp.loadCredentials(LocalContext.current)

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Enter username",
            fontSize = 24.sp,
            color = setTextFieldColor(isCorrect = userIsCorrect)
        )
        TextField(
            value = textUser,
            placeholder = { Text("UserName") },
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
            text = "Enter password",
            fontSize = 24.sp,
            color = setTextFieldColor(isCorrect = passIsCorrect)
        )
        TextField(
            value = textPass,
            placeholder = { Text("Password") },
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
/*                for (i in credentials.indices) {
                    if (credentials[i] == textUser && credentials[i] == textPass) {
                        navController.navigate(Routs.Profile.rout)
                    }
                }*/
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
                text = "Sign Up",
                fontSize = 26.sp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }
}
