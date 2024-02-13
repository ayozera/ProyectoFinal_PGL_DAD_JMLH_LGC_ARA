package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.MatchViewModel

@Composable
fun Match(navController: NavHostController) {
    val matchViewModel : MatchViewModel = viewModel()
    val context = LocalContext.current
    matchViewModel.setContext(context)
    val gameName = matchViewModel.getGameName()
    val gameArt = matchViewModel.getGameArt()
    val players by matchViewModel.players.collectAsStateWithLifecycle()
    val score = 0

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(gameName, gameArt)
        PlayersMarks(players, score)
    }
}

@Composable
fun PlayersMarks(players: ArrayList<Player>, score: Int) {
    players.forEach { player ->
        PlayerMark(player, score)
    }
}

@Composable
fun PlayerMark(player: Player, score: Int) {
    Row {
        Text(text = player.name)
        Icon(
            painter = painterResource(id = R.drawable.shuffle_fill0_wght400_grad0_opsz24),
            contentDescription = "restar",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                //TODO: Implement minus score
            }
        )
        Text(text = score.toString())
        Icon(
            painter = painterResource(id = R.drawable.shuffle_fill0_wght400_grad0_opsz24),
            contentDescription = "sumar",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                //TODO: Implement sum score
            }
        )
    }
}


