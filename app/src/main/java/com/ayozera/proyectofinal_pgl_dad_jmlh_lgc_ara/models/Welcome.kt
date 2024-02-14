package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@Composable
fun Welcome(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        ) {
            Title()
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(1f)
        ) {
            Logo()
            Text(
                text = "Bienvenid@",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.padding(30.dp))

            ButtonsWelcome(navController::navigate, Routs.LogIn.rout, "Iniciar Sesión")
            Spacer(modifier = Modifier.padding(20.dp))
            ButtonsWelcome(navController::navigate, Routs.SignUp.rout, "Crear Cuenta")
        }
    }
}



@Composable
fun Title() {
    Text(
        text = "Turns & Points",
        color = Color.White,
        fontSize = 32.sp,
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold

    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.dados),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
}

@Composable
fun ButtonsWelcome(navigate: (String) -> Unit, route: String, text: String) {
    Button(onClick = { navigate(route) }) {
        Text(text = text)
    }
}


