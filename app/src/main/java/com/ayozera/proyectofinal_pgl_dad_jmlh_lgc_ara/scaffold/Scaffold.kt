package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    navController: NavController,
    //loginModel: LoginModel,
    content: @Composable () -> Unit
) {
    Scaffold(

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(onClick = { /*TODO*/},
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(5.dp, 0.dp, 20.dp, 0.dp)
                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "go back arrow icon",
                                modifier = Modifier
                                    .size(28.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        TextButton(onClick = { /*TODO*/ }) {
                            Image(painter = painterResource(id = R.drawable.dados),
                                contentDescription = "Company Logo",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(48.dp)
                            )
                            Text(
                                text = "Turns & Points",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(onClick = {/*TODO*/}) {
                        Icon(
                            painter = painterResource(id = R.drawable.game_search),
                            contentDescription = "Search Player",
                            modifier = Modifier
                                .size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(onClick = {/*TODO*/}) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_add_box_24),
                            contentDescription = "Start New Game",
                            modifier = Modifier
                                .size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    TextButton(onClick = {/*TODO*/}) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_search),
                            contentDescription = "Search Game",
                            modifier = Modifier
                                .size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp),
        ) {
            content()
        }
    }
}

