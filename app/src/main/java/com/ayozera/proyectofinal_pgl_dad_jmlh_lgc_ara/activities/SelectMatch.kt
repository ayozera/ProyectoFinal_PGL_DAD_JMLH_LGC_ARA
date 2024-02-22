package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.SelectMatchViewModel
import kotlinx.coroutines.launch
import java.sql.SQLOutput
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectMatch(navController: NavHostController, appMainViewModel: AppMainViewModel) {

    val selectMatchViewModel: SelectMatchViewModel = viewModel()
    val context = LocalContext.current
    val games by selectMatchViewModel.games.collectAsStateWithLifecycle()
    val players by selectMatchViewModel.playersDB.collectAsStateWithLifecycle()
    games.forEach { game ->
        println(game.name)
    }
    players.forEach { player ->
        println(player.name)
    }
    var openDialogError by remember { mutableStateOf(false) }
    val playersName = arrayListOf<String>()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {


        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Menu(navController = navController, appMainViewModel)
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selecciona el juego y los jugadores",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(30.dp))
                GameSelection(games) { onGameSelected ->
                    selectMatchViewModel.setGameId(onGameSelected)
                }
                Spacer(modifier = Modifier.size(30.dp))
                PlayerSelection(players, playersName, selectMatchViewModel)
                Spacer(modifier = Modifier.size(30.dp))
                ButtonBegin {
                    var check = true
                    playersName.forEach { player ->
                        if (player.isEmpty()) {
                            check = false
                        }
                    }
                    if (selectMatchViewModel.isGameSelected() && check) {
                        playersName.forEach { player ->
                            players.forEach { playerInList ->
                                if (player == playerInList.name) {
                                    selectMatchViewModel.addPlayers(playerInList)
                                }
                            }
                        }
                        selectMatchViewModel.setDate(LocalDate.now())
                        selectMatchViewModel.saveSelections(context)
                        appMainViewModel.startMatch()
                        navController.navigate(Routs.Match.rout)
                    } else {
                        openDialogError = true
                    }
                }
                Spacer(modifier = Modifier.size(80.dp))

                if (openDialogError) {
                    AlertDialogError() {
                        openDialogError = false
                    }
                }
            }
        }
    }
}

@Composable
fun GameSelection(games: ArrayList<GameDB>, onGameSelection: (GameDB) -> Unit) {
    var expandedGame by remember { mutableStateOf(false) }
    var selectedGame = remember { mutableStateOf(GameDB()) }

    Column {

        Text(
            text = "El nombre del juego",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedGame.value.name,
            onValueChange = {},
            label = { Text("¿Cuál es el juego?") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    expandedGame = true
                })
                {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de juegos"
                    )
                }
                DropdownMenu(expanded = expandedGame, onDismissRequest = { expandedGame = false }) {
                    games.forEach { game ->
                        DropdownMenuItem(text = { Text(text = game.name) }, onClick = {
                            selectedGame.value = game
                            expandedGame = false
                            onGameSelection(game)
                        })
                    }
                }
            }
        )
    }
}

@Composable
fun PlayerSelection(
    players: ArrayList<PlayerDB>,
    playersName: ArrayList<String>,
    selectMatchViewModel: SelectMatchViewModel
) {
    var numberOfPlayers by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberSelection() { onNumberSelected ->
            selectMatchViewModel.clearPlayers()
            playersName.clear()
            for (i in 0 until onNumberSelected.toInt()) {
                playersName.add("")
            }
            numberOfPlayers = onNumberSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        if (numberOfPlayers.toInt() > 0) {
            Text(
                text = "Introduce el nombre de los jugadores",
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
        for (i in 0 until numberOfPlayers.toInt()) {
            OnePlayerSelection(players) { onPlayerSelected ->
                playersName[i] = onPlayerSelected
            }
            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}

@Composable
fun NumberSelection(onNumberSelection: (String) -> Unit) {
    var numbers = arrayListOf<String>("1", "2", "3", "4", "5", "6", "7", "8")
    var expandedNumber by remember { mutableStateOf(false) }
    var selectedNumber by remember { mutableStateOf("") }
    Column {
        Text(
            text = "Introduce el número de jugadores",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedNumber,
            onValueChange = {},
            label = { Text("Número de jugadores") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedNumber = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores"
                    )
                }
                DropdownMenu(expanded = expandedNumber,
                    onDismissRequest = { expandedNumber = false }) {
                    numbers.forEach { number ->
                        DropdownMenuItem(text = { Text(text = number) }, onClick = {
                            selectedNumber = number
                            expandedNumber = false
                            onNumberSelection(number)
                        })
                    }
                }
            }
        )
    }
}

@Composable
fun OnePlayerSelection(players: ArrayList<PlayerDB>, onPlayerSelection: (String) -> Unit) {
    var expandedPlayer by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf("") }
    Column {
        TextField(value = selectedPlayer,
            onValueChange = {},
            label = { Text("Seleccione un jugador") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedPlayer = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores"
                    )
                }
                DropdownMenu(expanded = expandedPlayer,
                    onDismissRequest = { expandedPlayer = false }) {
                    players.forEach { player ->
                        DropdownMenuItem(text = { Text(text = player.name) }, onClick = {
                            selectedPlayer = player.name
                            expandedPlayer = false
                            onPlayerSelection(player.name)
                        })
                    }
                }
            }
        )
    }
}

@Composable
fun AlertDialogError(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(
                        "Se deben llenar todos los campos",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
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
fun ButtonBegin(onSave: () -> Unit) {
    Button(
        onClick = { onSave() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Text(text = "Empezar Partida", color = MaterialTheme.colorScheme.primary)
    }
}

