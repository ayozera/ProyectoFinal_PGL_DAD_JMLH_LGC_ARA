package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.GameDescriptionViewModel
import kotlinx.coroutines.launch


@Composable
fun GameDescription(
    navController: NavHostController,
    appMainViewModel: AppMainViewModel,
    gameName: String?
) {
    val gameViewModel = remember { GameDescriptionViewModel() }
    //Cargamos el juego y sus comentarios en el viemodel en un hilo que sólo se ejecuta una vez
    val player by appMainViewModel.playerDB!!.collectAsState()
    gameViewModel.loadViewModel(gameName!!, player!!)
    val game by gameViewModel.game.collectAsState()
    val comments by gameViewModel.listComment.collectAsState()
    val gameArt = LocalContext.current.resources.getIdentifier(
        gameName.replace(" ", "_").lowercase(),
        "drawable",
        LocalContext.current.packageName
    )
    val description by gameViewModel.description.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                    ReviewList(comments, gameViewModel)
                }
            }
        )
    }
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
            onClick = {
                viewModel.addComment(userReview)
                userReview = ""
            },
            modifier = Modifier
                .padding(8.dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.shapes.extraLarge
                )
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
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ReviewBox(
    comments: Comment,
    isDeleteable: Boolean,
    gameViewModel: GameDescriptionViewModel,
    i: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = false)
    val anchors = mapOf(
        0f to false,
        -300f to true
    )
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = isDeleteable,
                reverseDirection = true
            )
            .offset(x = with(LocalDensity.current) { swipeableState.offset.value.dp })
    ) {
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
                .padding(16.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Espacio vertical entre elementos
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
        if (swipeableState.currentValue) {
            showDialog = true // Muestra el cuadro de diálogo cuando se desliza el comentario
        }
    }
    Spacer(modifier = Modifier.size(10.dp))

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmación") },
            text = { Text("¿Estás seguro de que quieres borrar este comentario?") },
            confirmButton = {
                TextButton(onClick = {
                    gameViewModel.deleteComment(i)
                    coroutineScope.launch {
                        swipeableState.animateTo(false)
                        showDialog = false
                    }

                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                     // Aquí actualizamos showDialog antes de cerrar el diálogo
                    coroutineScope.launch {
                        swipeableState.animateTo(false)
                        showDialog = false
                    }
                }) {
                    Text("No")
                }
            }
        )
    }
}



@Composable
fun ReviewList(comments: List<Comment>, gameViewModel: GameDescriptionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        var i = 0
        comments.forEach { comment ->
            var isDeleteable = gameViewModel.isDeletable(i)
            ReviewBox(comment, isDeleteable, gameViewModel, i)
            i++
        }
    }
}

@Composable
fun deleteReview() {

}
