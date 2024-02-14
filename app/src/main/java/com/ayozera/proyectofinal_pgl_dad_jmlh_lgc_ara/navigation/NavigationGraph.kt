package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
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
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel.AppMainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController: NavHostController = rememberNavController()
    val appMainViewModel: AppMainViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routs.JukeBox.rout) {

        composable(Routs.LogIn.rout) {
            LogIn(navController = navController, appMainViewModel)
        }
        composable(Routs.SignUp.rout) {
            SignUp(navController = navController, appMainViewModel)
        }
        composable(Routs.Profile.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                Profile(navController = navController, appMainViewModel)
            }
        }
        composable(Routs.Game.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                GameDescription(navController = navController, appMainViewModel)
            }
        }
        composable(Routs.SearchBar.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                SearchBar(navController = navController, appMainViewModel)
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
                JukeBox(navController = navController, appMainViewModel)
            }
        }
        composable(Routs.Credits.rout) {
            MyScaffold(navController = navController, appMainViewModel) {
                Credits(navController = navController, appMainViewModel)
            }

        }
    }
}





