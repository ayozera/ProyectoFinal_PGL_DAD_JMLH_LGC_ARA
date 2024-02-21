package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.GameDescriptionViewModel


@Composable
fun GameDescription(
    navController: NavHostController,
    appMainViewModel: AppMainViewModel,
    gameName: String?
) {
    val gameViewModel = remember { GameDescriptionViewModel() }
    gameViewModel.loadGame(gameName!!)
    val game by gameViewModel.game.collectAsState()
    val comments by gameViewModel.listComment.collectAsState()
    val gameArt = LocalContext.current.resources.getIdentifier(
        gameName?.replace(" ", "_")?.lowercase(),
        "drawable",
        LocalContext.current.packageName
    )
    println("Nombre del juego: $gameName")
    val description = game?.description ?: "No hay descripción"
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu(navController = navController, appMainViewModel)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                GameHeader(gameName, gameArt)
                Description(description)
                WriteReview(gameViewModel)
                ReviewList(comments)
            }
        }
    )
}

@Composable
fun GameHeader(gameName: String, gameArt: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gameName,
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.tertiary,
            fontWeight = FontWeight.ExtraBold
        )
        Image(
            painter = painterResource(
                id = gameArt
            ),
            contentDescription = "Portada del Juego",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(300.dp)
        )
    }
}


@Composable
fun Description(description: String) {
    var showdescription by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        ) {
            Text(
                text = "Descripción",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextButton(onClick = { showdescription = !showdescription }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_drop_down),
                    contentDescription = "Expandir Descripción",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary

                )
            }
        }
        if (showdescription) {
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
@Composable
fun WriteReview(viewModel: GameDescriptionViewModel) {
    var userReview by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            value = userReview,
            onValueChange = { userReview = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            onClick = { viewModel.addComment(userReview)
                      userReview = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.primaryContainer),

        ) {
            Text(
                text = "Comentar",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ReviewBox(comments: Comment) {
        Column(
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
        ) {
            Text(
                text = comments.player,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = comments.comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = comments.date,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }


@Composable
fun ReviewList(comments: List<Comment>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        comments.forEach { comment ->
            ReviewBox(comment)
        }
    }
}
