package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(gameName, gameArt)
        PlayersMarks(matchViewModel)
    }
}

@Composable
fun PlayersMarks(matchViewModel: MatchViewModel) {
    val players by matchViewModel.players.collectAsStateWithLifecycle()
    players.forEach { player ->
        PlayerMark(matchViewModel, player)
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@Composable
fun PlayerMark(matchViewModel: MatchViewModel, player: Player) {

    val score by matchViewModel.score.collectAsStateWithLifecycle()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, player.color, CircleShape)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
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
        Text(text = player.name)
        Icon(
            painter = painterResource(id = R.drawable.remove),
            contentDescription = "restar",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                matchViewModel.substractScore()
            }
        )
        Text(text = score.toString())
        Icon(
            painter = painterResource(id = R.drawable.round_add_box_24),
            contentDescription = "sumar",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                matchViewModel.addScore()
            }
        )
    }
}


