package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.scaffold.MyScaffold
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Credits
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.GameDescription
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.JukeBox
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.LogIn
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Profile
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SearchBar
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SelectMatch
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SignUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Welcome
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.JukeBoxViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.LogInViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController: NavHostController = rememberNavController()
    val appMainViewModel: AppMainViewModel = viewModel()
    val exoPlayerViewModel: JukeBoxViewModel = viewModel()
    val corutinaScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        corutinaScope.launch {
            exoPlayerViewModel.createPlayer(context, exoPlayerViewModel)
        }
    }
    val logInViewModel: LogInViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routs.Welcome.rout) {

        composable(Routs.Welcome.rout) {

                Welcome(navController = navController)
            }

        composable(Routs.LogIn.rout) {
            LogIn(navController = navController, appMainViewModel)
        }
        composable(Routs.SignUp.rout) {
            SignUp(navController = navController)
        }
        composable(Routs.Profile.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                Profile(navController = navController, appMainViewModel)
            }
        }
        composable("${Routs.Game.rout}/{gameName}") { backStackEntry ->
            val gameName = backStackEntry.arguments?.getString("gameName")
            MyScaffold(navController = navController, appMainViewModel) {
                GameDescription(navController = navController, appMainViewModel, gameName)
            }
        }
        composable(Routs.SearchBar.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                SearchBar(navController = navController)
            }
        }
        composable(Routs.Match.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                Match(navController = navController, appMainViewModel)
            }
        }
        composable(Routs.SelectMatch.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                SelectMatch(navController = navController, appMainViewModel)
            }
        }
        composable(Routs.JukeBox.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                JukeBox(navController = navController, exoPlayerViewModel, appMainViewModel)
            }
        }
        composable(Routs.Credits.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                Credits(navController = navController, appMainViewModel)
            }

        }
    }
}





