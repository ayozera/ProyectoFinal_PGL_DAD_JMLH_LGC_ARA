package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
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
        Spacer(modifier = Modifier.weight(2f))
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
        Spacer(modifier = Modifier.weight(1f))
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
