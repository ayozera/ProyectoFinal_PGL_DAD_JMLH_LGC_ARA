package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.MatchViewModel
import kotlinx.coroutines.launch

@Composable
fun Match(navController: NavHostController, appMainViewModel: AppMainViewModel) {

    val matchViewModel: MatchViewModel = viewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    matchViewModel.loadViewModel(context)
    val gameName = matchViewModel.getGameName()
    val gameArt = LocalContext.current.resources.getIdentifier(
        matchViewModel.getGameArt(),
        "drawable",
        LocalContext.current.packageName
    )
    var openDialog by remember { mutableStateOf(false) }
    var openDialog2 by remember { mutableStateOf(false) }
    var isEnabled by remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val courutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameHeader(gameName, gameArt)
                PlayersMarks(matchViewModel)
                ButtonSaveMatch(isEnabled) {
                    courutineScope.launch {
                        matchViewModel.saveMatch()
                        appMainViewModel.discardMatch()
                        isEnabled = false
                        openDialog = true
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                ButtonDiscardMatch(isEnabled) {
                    openDialog2 = true
                }
                Spacer(modifier = Modifier.size(60.dp))

                if (openDialog) {
                    AlertDialogSaveMatch() {
                        openDialog = false
                    }
                }

                if (openDialog2) {
                    AlertDialogDiscardMatch(onConfirmClick = {
                        openDialog2 = false
                        appMainViewModel.discardMatch()
                        navController.navigate(Routs.Profile.rout)
                    }, onCancelClick = {
                        openDialog2 = false
                    })
                }
            }
        }
    )
}


@Composable
fun PlayersMarks(matchViewModel: MatchViewModel) {
    val players by matchViewModel.players.collectAsStateWithLifecycle()
    var index by remember { mutableIntStateOf(0) }
    for (i in 0 until players.size) {
        PlayerMark(matchViewModel, players[i], remember { mutableIntStateOf(index) })
        Spacer(modifier = Modifier.size(10.dp))
        index++
    }
}

@Composable
fun PlayerMark(matchViewModel: MatchViewModel, player: Player, index: MutableState<Int>) {

    val score by matchViewModel.score.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, player.color, CircleShape)
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageResourceId = LocalContext.current.resources.getIdentifier(
            player.avatar,
            "drawable",
            LocalContext.current.packageName
        )

        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "avatar",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = player.name,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Row {
            Icon(
                painter = painterResource(id = R.drawable.remove_box),
                contentDescription = "restar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { matchViewModel.substractScore(index.value) },
                            onLongPress = { matchViewModel.substractFiveScore(index.value) }
                        )
                    }
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = score[index.value].toString(),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.round_add_box_24),
                contentDescription = "sumar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { matchViewModel.addScore(index.value) },
                            onLongPress = { matchViewModel.addFiveScore(index.value) }
                        )
                    }
                    .size(30.dp)
            )
        }
    }
}

@Composable
fun ButtonDiscardMatch(isEnabled: Boolean, onSave: () -> Unit) {
    Button(
        onClick = { onSave() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), enabled = isEnabled
    ) {
        Text(text = "Descartar Partida", color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ButtonSaveMatch(isEnabled: Boolean, onSave: () -> Unit) {
    Button(
        onClick = { onSave() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), enabled = isEnabled
    ) {
        Text(text = "Guardar Partida", color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun AlertDialogSaveMatch(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Partida guardada")
                },
                text = {
                    Text("Partida añadida correctamente")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismissClick()
                        }) {
                        Text("Entendido")
                    }
                }
            )
        }
    }
}

@Composable
fun AlertDialogDiscardMatch(onConfirmClick: () -> Unit, onCancelClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onCancelClick()
                },
                title = {
                    Text(text = "Partida descartada")
                },
                text = {
                    Text("¿Está seguro de que desea eliminar esta partida?")
                },
                confirmButton = {
                    Row {
                        Button(
                            onClick = {
                                onConfirmClick()
                            }) {
                            Text("Continuar")
                        }
                        Button(
                            onClick = {
                                onCancelClick()
                            }) {
                            Text("Cancelar")
                        }
                    }
                }
            )
        }
    }
}


