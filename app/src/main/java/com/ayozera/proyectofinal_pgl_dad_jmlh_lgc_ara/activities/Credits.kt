package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs

@Composable
fun Credits(navController: NavHostController) {
    Column (
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

@Composable
fun CreditsHeader() {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(0.dp, 16.dp)) {
        Text(text = "Turns & Points",
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.primary)
        Image(painter = painterResource(id = R.drawable.dados), contentDescription = "Company Logo")
    }
}

@Composable
fun DescriptionCredits(){
    Column (modifier = Modifier.padding(4.dp,0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "This app was developed by a group of students from the CIFT César Manrique. This app was developed as a final project for the courses PGL & DAD. The app was developed using Jetpack Compose and Kotlin.\n\n This app was developed by the students:",
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
        Text(text = "Version 0.7.23",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.inversePrimary)
    }
}