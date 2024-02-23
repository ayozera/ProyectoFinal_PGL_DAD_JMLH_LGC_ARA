package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.JukeBoxViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@Composable
fun JukeBox(
    navController: NavHostController,
    exoPlayerViewModel: JukeBoxViewModel,
    appMainViewModel: AppMainViewModel
) {

    val context = LocalContext.current
    val songs = exoPlayerViewModel.canciones.collectAsState()
    val index = exoPlayerViewModel.index.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
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
                                steps = ((durationMinutes * 60) + durationSeconds),
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

                    var isRepeatOn by remember { mutableStateOf(false) }
                    var isShuffleOn by remember { mutableStateOf(false) }
                    var isPlaying by remember { mutableStateOf(exoPlayerViewModel.mediaPlayer.value!!.isPlaying) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.shuffle_fill0_wght400_grad0_opsz24),
                            contentDescription = "Random",
                            tint = if(isShuffleOn) Color.Green
                            else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable {
                                isShuffleOn = !isShuffleOn
                                exoPlayerViewModel.clicRandom()
                            }
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_ios_fill0_wght400_grad0_opsz24),
                            contentDescription = "Anterior",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable { exoPlayerViewModel.clicBefore(context = context) }
                        )

                        val playIcon = rememberVectorPainter(image = Icons.Filled.PlayArrow)
                        val pauseIcon =
                            painterResource(id = R.drawable.pause_fill0_wght400_grad0_opsz24)

                        val playPauseIcon = if (isPlaying) pauseIcon else playIcon

                        Icon(
                            painter = playPauseIcon,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier
                                .clickable {
                                    isPlaying = !isPlaying
                                    exoPlayerViewModel.clicPlay(context = context)
                                }
                                .size(60.dp),
                            tint = if (isPlaying) Color.Green
                            else MaterialTheme.colorScheme.onBackground,

                            )

                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward_ios_fill0_wght400_grad0_opsz24),
                            contentDescription = "Siguiente",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable { exoPlayerViewModel.clicNext(context = context) }
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.repeat_fill0_wght400_grad0_opsz24),
                            contentDescription = "Repetir",
                            tint = if (isRepeatOn) Color.Green
                            else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.clickable {
                                isRepeatOn = !isRepeatOn
                                exoPlayerViewModel.clicBucle()
                            }
                        )
                    }
                }
            }
        }
    )
}