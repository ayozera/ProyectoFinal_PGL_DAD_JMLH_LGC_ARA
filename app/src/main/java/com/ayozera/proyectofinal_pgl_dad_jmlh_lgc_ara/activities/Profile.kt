package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.ProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Preview
@Composable
fun Profile() {
//fun Profile(navController: NavHostController) {
    val profileViewModel = ProfileViewModel()
    val context = LocalContext.current
    profileViewModel.setContext(context)
    val userName = profileViewModel.userName.value
    val userAvatar = profileViewModel.userAvatar.value
    val matches = profileViewModel.matches.value
    val favouriteGame = profileViewModel.favouriteGame.value

    var isEditClicked by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()

            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
        ) {
            //ArrowBackWelcome(navController)
            ArrowBackWelcome()
            Text(
                text = "Turn & points",
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 25.dp)
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
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "My Profile",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(R.drawable.imagen_perfil),
//                painter = painterResource (Integer.parseInt(userAvatar)),
                    contentDescription = "picture of player",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(20.dp))

                Column() {
                    Text(
                        text = "Username: ",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = userName,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { isEditClicked = !isEditClicked }) {
                    Text(text = "Edit")
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (isEditClicked) {
                Button(onClick = { /* código para eliminar cuenta */ }) {
                    Text(text = "Delete profile")
                }
            }
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
            Text(
                text = "Favourite game: ",
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = favouriteGame,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = "Recent games: ",
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
//        LazyColumn() {
//            items(matches.size) { index ->
//                Text(
//                    text = "matches[index].name",
//                    fontSize = 24.sp,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            }
//        }


    }
}

@Composable
fun ArrowBackWelcome() {
//fun ArrowBackWelcome(navController: NavHostController) {

    Box() {
        IconButton(
            onClick = { },
//            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onTertiaryContainer)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
    }
}

