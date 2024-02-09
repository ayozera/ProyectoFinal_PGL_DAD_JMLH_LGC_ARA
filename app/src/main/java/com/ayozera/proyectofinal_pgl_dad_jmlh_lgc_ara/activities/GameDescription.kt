package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R

@Composable
fun GameDescription (navController: NavHostController){

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun header(){
    Column {
        Text(
            text = "GameName",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Image(painter = painterResource(
            id = R.drawable.cluedo),
            contentDescription = "Game Art",
            modifier = Modifier
                .size(240.dp)
                .padding(24.dp))

    }
}