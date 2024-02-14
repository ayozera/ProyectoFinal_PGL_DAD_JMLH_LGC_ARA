package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Menu
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@Composable
fun Credits(navController: NavHostController, appMainViewModel: AppMainViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
        },
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreditsHeader()
                DescriptionCredits()
                StudentsCredits()
                VersionCredits()
            }
        }
    )
}

@Composable
fun CreditsHeader() {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(0.dp, 16.dp)) {
        Text(text = "Turns & Points",
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.primary)
        Image(painter = painterResource(id = R.drawable.dados), contentDescription = "Logo Turns & Points")
    }
}

@Composable
fun DescriptionCredits(){
    Column (modifier = Modifier.padding(4.dp,0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Esta aplicación ha sido desarrollada por un grupo de estudiantes del CIFT César Manrique. Esta aplicación es el proyecto final de los cursos PGL y DAD. La aplicación fue desarrollada usando Jetpack Compose y Kotlin.\n Esta aplicación fue desarrollada por los estudiantes:",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer)
    }
}
@Composable
fun StudentsCredits(){
    Column (modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Ayoze Rodriguez Alvarez",
            modifier = Modifier.padding(0.dp,4.dp),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary)
        Text(text = "Juan Marcos León Hernández",
            modifier = Modifier.padding(0.dp,4.dp),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary)
        Text(text = "Lorena García Castilla",
            modifier = Modifier.padding(0.dp,4.dp),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary)
    }
}
@Composable
fun VersionCredits(){
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(0.dp,4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom) {
        Text(text = "14/02/2024",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.inversePrimary)
        Text(text = "Versión 0.7.23",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.inversePrimary)
    }
}