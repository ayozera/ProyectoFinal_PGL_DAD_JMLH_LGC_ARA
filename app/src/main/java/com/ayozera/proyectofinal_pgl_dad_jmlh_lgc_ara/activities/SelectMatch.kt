package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.SelectMatchViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SelectMatch(navController: NavHostController) {

    val selectMatchViewModel: SelectMatchViewModel = viewModel()
    val context = LocalContext.current
    selectMatchViewModel.setContext(context)
    val games = selectMatchViewModel.getGames()
    val players = selectMatchViewModel.getPlayers()
    val selectedGame = selectMatchViewModel.game
    var openDialogError by remember { mutableStateOf(false) }
    val playersName = arrayListOf<String>()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(30.dp))
        GameSelection(games) { onGameSelected ->
            selectMatchViewModel.setGame(onGameSelected)
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
            if (selectedGame.value.isNotEmpty() && check) {
                playersName.forEach { player ->
                    players.forEach { playerInList ->
                        if (player == playerInList.name) {
                            selectMatchViewModel.addPlayers(playerInList)
                        }
                    }
                }
                selectMatchViewModel.setDate(LocalDate.now())
                selectMatchViewModel.saveSelections()
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

@Composable
fun GameSelection(games: List<String>, onGameSelection: (String) -> Unit) {
    var expandedGame by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf("") }
    Column {

        Text(
            text = "El nombre del juego",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedGame,
            onValueChange = {},
            label = { Text("¿Cuál es el juego?") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedGame = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de juegos"
                    )
                }
                DropdownMenu(expanded = expandedGame, onDismissRequest = { expandedGame = false }) {
                    games.forEach { game ->
                        DropdownMenuItem(text = { Text(text = game) }, onClick = {
                            selectedGame = game
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
    players: ArrayList<Player>,
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
fun OnePlayerSelection(players: ArrayList<Player>, onPlayerSelection: (String) -> Unit) {
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

