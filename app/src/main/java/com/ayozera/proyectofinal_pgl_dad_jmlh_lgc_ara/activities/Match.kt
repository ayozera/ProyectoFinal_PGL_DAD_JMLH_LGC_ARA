package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.MatchViewModel

@Composable
fun Match(navController: NavHostController) {
    val matchViewModel : MatchViewModel = viewModel()
    val context = LocalContext.current
    matchViewModel.setContext(context)
    val gameName = matchViewModel.getGameName()
    val gameArt = matchViewModel.getGameArt()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameHeader(gameName, gameArt)
    }
}

