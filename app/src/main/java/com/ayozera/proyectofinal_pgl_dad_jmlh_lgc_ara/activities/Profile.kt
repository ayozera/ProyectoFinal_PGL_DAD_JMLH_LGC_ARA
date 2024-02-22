package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.ProfileViewModel


@Composable
fun Profile(navController: NavHostController, appMainViewModel: AppMainViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val user = appMainViewModel.playerDB.collectAsState()
    val profileViewModel : ProfileViewModel = viewModel()
    LaunchedEffect(key1 = Unit) {
        profileViewModel.load(user.value!!.id)
    }
    val matches = profileViewModel.matches.collectAsStateWithLifecycle()
    var isEditClicked by remember { mutableStateOf(false) }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
            ) {
                ProfileHeader(appMainViewModel)
                ProfileBody()
                MatchList(matches.value)
            }
        }
    )
}

@Composable
fun ProfileHeader(appMainViewModel: AppMainViewModel) {
    val user = appMainViewModel.player!!.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        ProfileAlertDialogError { showDialog = false }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val imageResourceId = LocalContext.current.resources.getIdentifier(
            user.value!!.avatar,
            "drawable",
            LocalContext.current.packageName
        )
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(125.dp)
                .border(3.dp, user!!.value!!.color, shape = CircleShape)
                .clip(CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
            ) {
                Text(
                    text = user.value!!.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                TextButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primary),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Expandir Descripción",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Text(
                text = "Cluedo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .alpha(0.8f)
            )
        }
    }
}

@Composable
fun ProfileBody() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Partidas Jugadas",
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
    }

}

@Composable
fun ProfileAlertDialogError(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Error") },
        text = { Text(text = "Aun estamos trabajando en esta funcionalidad, disculpe las molestias.") },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}
@Composable
fun MatchList(matchs: List<Match>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(matchs) { match ->
            MatchBox(match)
        }
    }
}

@Composable
fun MatchBox(match: Match) {
    var players = match.players.size
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                2.dp,
                MaterialTheme.colorScheme.onPrimaryContainer,
                MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp, 10.dp)
    ){
        val imageResourceId = LocalContext.current.resources.getIdentifier(
            match.gameArt,
            "drawable",
            LocalContext.current.packageName
        )
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Game Art",

            modifier = Modifier
                .size(110.dp)
                .clip(MaterialTheme.shapes.large)
                .padding(0.dp, 0.dp, 10.dp, 0.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row (horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()){
                Text(
                    text = match.game,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${match.day}/${match.month}/${match.year}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.size(4.dp))

            for (i in players-1 downTo 0) {
                Text(
                    text = match.players[i].name + ": " + match.score[i].toString() + " puntos",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
}
