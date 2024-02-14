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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@Composable
fun Menu(navController: NavHostController, appMainViewModel: AppMainViewModel) {

    val isMatchRunning = appMainViewModel.isMatching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.75f)
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        CardProfile(appMainViewModel, Modifier.weight(2f))
        Spacer(modifier = Modifier.weight(0.25f))
        Column(
            modifier = Modifier.weight(4f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonMenu("Perfil") {
                navController.navigate(Routs.Profile.rout)
            }
            ButtonMenu("Búsqueda") {
                navController.navigate(Routs.SearchBar.rout)
            }
            ButtonMenu("Partida"){
                if (isMatchRunning.value) {
                    navController.navigate(Routs.Match.rout)
                } else {
                    navController.navigate(Routs.SelectMatch.rout)
                }
            }
            ButtonMenu("Reproductor") {
                navController.navigate(Routs.JukeBox.rout)
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonMenu("Créditos"){
                navController.navigate(Routs.Credits.rout)
            }
            ButtonMenu("Cerrar sesión"){
                appMainViewModel.logOut()
                navController.navigate(Routs.LogIn.rout)
            }
        }
        Spacer(modifier = Modifier.weight(0.75f))
    }
}

@Composable
fun CardProfile(appMainViewModel: AppMainViewModel, weight: Modifier) {
    val user = appMainViewModel.user?.collectAsState()

    Row(
        weight.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight()
                .background(user!!.value!!.color, shape = MaterialTheme.shapes.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val imageResourceId = LocalContext.current.resources.getIdentifier(
                user.value!!.avatar,
                "drawable",
                LocalContext.current.packageName
            )
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(125.dp)
                    .border(3.dp, user.value!!.color, shape = CircleShape)
                    .clip(CircleShape)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(MaterialTheme.colorScheme.onTertiary, shape = MaterialTheme.shapes.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = user!!.value!!.name, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}


@Composable
fun ButtonMenu(text: String, onButtonClick : () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(0.5f),
        onClick = { onButtonClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.primary)
    }
}
