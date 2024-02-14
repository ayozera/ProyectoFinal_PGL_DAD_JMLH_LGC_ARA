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
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.ProfileViewModel


@Composable
fun Profile(navController: NavHostController, appMainViewModel: AppMainViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val profileViewModel = ProfileViewModel()
    val context = LocalContext.current
    profileViewModel.setContext(context)
    /*    val userName = profileViewModel.userName.value
        val userAvatar = profileViewModel.userAvatar.value
        val matches = profileViewModel.matches.value
        val favouriteGame = profileViewModel.favouriteGame.value*/

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
            }
        }
    )
}

@Composable
fun ProfileHeader(appMainViewModel: AppMainViewModel) {
    val user = appMainViewModel.user?.collectAsState()
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
        Image(
            painter = painterResource(id = user!!.value.avatar.toInt()),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(125.dp)
                .border(3.dp, user.value.color, shape = CircleShape)
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
                    text = user.value.name,
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
            modifier = Modifier
                .padding(10.dp)
        )
        MatchList(comments = listOf())
    }

}

@Composable
fun ProfileAlertDialogError(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Error") },
        text = { Text(text = "An error occurred") },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}
@Composable
fun MatchList(comments: List<Comment>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(comments) { comment ->
            ReviewBox(comment)
        }
    }
}

@Composable
fun MatchBox(comments: Comment) {
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
            text = comments.user,
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
