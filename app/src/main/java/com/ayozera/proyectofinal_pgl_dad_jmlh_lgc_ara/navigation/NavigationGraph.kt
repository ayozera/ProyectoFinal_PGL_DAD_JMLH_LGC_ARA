package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.scaffold.MyScaffold
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Credits
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.GameDescription
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Invitations
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.JukeBox
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.LogIn
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Profile
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Score
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SearchBar
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SelectMatch
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.SignUp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routs.LogIn.rout) {

        composable(Routs.LogIn.rout) {
            LogIn(navController = navController)
        }
        composable(Routs.SignUp.rout) {
            SignUp(navController = navController)
        }
        composable(Routs.Profile.rout) {
            MyScaffold(navController = navController) {
                Profile(navController = navController)
            }
        }
        composable(Routs.Game.rout) {
            MyScaffold(navController = navController) {
                GameDescription(navController = navController)
            }
        }
        composable(Routs.SearchBar.rout) {
            MyScaffold(navController = navController) {
                SearchBar(navController = navController)
            }
        }
        composable(Routs.Match.rout) {
            MyScaffold(navController = navController) {
                Match(navController = navController)
            }
        }
        composable(Routs.SelectMatch.rout) {
            MyScaffold(navController = navController) {
                SelectMatch(navController = navController)
            }
        }
        composable(Routs.Score.rout) {
            MyScaffold(navController = navController) {
                Score(navController = navController)
            }

        }
        composable(Routs.Invitations.rout) {
            MyScaffold(navController = navController) {
                Invitations(navController = navController)
            }
        }
        composable(Routs.JukeBox.rout) {
            MyScaffold(navController = navController) {
                JukeBox(navController = navController)
            }
        }
        composable(Routs.Credits.rout) {
            MyScaffold(navController = navController) {
                Credits(navController = navController)
            }

        }
    }
}





