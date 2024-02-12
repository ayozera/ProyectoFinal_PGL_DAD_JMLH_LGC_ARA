package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.JukeBoxViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun JukeBox(navController: NavHostController) {

    val exoPlayerViewModel: JukeBoxViewModel = viewModel()
    val context = LocalContext.current
    val songs = exoPlayerViewModel.canciones.collectAsState()
    val index = exoPlayerViewModel.index.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            //TODO: Implementar la barra de busqueda
        },
        content = {
            Column(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    val songName = songs.value[index.value].name
                    val songImage = songs.value[index.value].image

                    Text(
                        text = "Reproduciendo ahora",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    val resourceId = context.resources.getIdentifier(
                        songImage,
                        "drawable",
                        context.packageName
                    )
                    Image(
                        painter = painterResource(id = resourceId),
                        contentDescription = "Album de $songName",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    )

                    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

                        Text(
                            text = songName,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Column(modifier = Modifier.padding(top = 50.dp)) {

                            val durationMinutes by exoPlayerViewModel.duracionMinutos.collectAsStateWithLifecycle()
                            val durationSeconds by exoPlayerViewModel.duracionSegundos.collectAsStateWithLifecycle()
                            val positionSeconds by exoPlayerViewModel.posicionSegundos.collectAsStateWithLifecycle()
                            val positionMinutes by exoPlayerViewModel.posicionMinutos.collectAsStateWithLifecycle()

                            var progress by remember { mutableFloatStateOf(0f) }

                            if (durationMinutes > 0 && durationSeconds >= 0 && positionMinutes >= 0 && positionSeconds >= 0) {
                                progress =
                                    ((positionMinutes * 60) + positionSeconds).toFloat() /
                                            ((durationMinutes * 60) + durationSeconds).toFloat()
                            }

                            Slider(
                                value = progress,
                                onValueChange = { nuevoProgreso ->
                                    progress = nuevoProgreso
                                    val newPositionSeconds =
                                        (progress * ((durationMinutes * 60) + durationSeconds)).toInt()
                                    exoPlayerViewModel.moveSlider(newPositionSeconds)
                                },
                                valueRange = 0f..1f,
                                steps = ((durationMinutes * 60) + durationSeconds) ?: 0,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color.Green
                                )
                            )

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = if (positionSeconds < 10) "$positionMinutes:0$positionSeconds" else "$positionMinutes:$positionSeconds",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = if (durationSeconds < 10) "$durationMinutes:0$durationSeconds" else "$durationMinutes:$durationSeconds",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


