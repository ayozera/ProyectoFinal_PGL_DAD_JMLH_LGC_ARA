package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.credits
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.game
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.invitations
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.jukeBox
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.logIn
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.profile
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.score
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.searchBar
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.selectMatch
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.signUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.scaffold.MyScaffold

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navigationGraph() {
    val navController: NavHostController = rememberNavController()

    MyScaffold(navController = navController) {

        NavHost(navController = navController, startDestination = Routs.LogIn.rout) {
            composable(Routs.Profile.rout) {
                profile(navController = navController)
            }
            composable(Routs.Game.rout) {
                game(navController = navController)
            }
            composable(Routs.SearchBar.rout) {
                searchBar(navController = navController)
            }
            composable(Routs.Match.rout) {
                match(navController = navController)
            }
            composable(Routs.SelectMatch.rout) {
                selectMatch(navController = navController)
            }
            composable(Routs.Score.rout) {
                score(navController = navController)
            }
            composable(Routs.Invitations.rout) {
                invitations(navController = navController)
            }
            composable(Routs.JukeBox.rout) {
                jukeBox(navController = navController)
            }
            composable(Routs.Credits.rout) {
                credits(navController = navController)
            }
        }
    }

    NavHost(navController = navController, startDestination = Routs.LogIn.rout) {
        composable(Routs.LogIn.rout) {
            logIn(navController = navController)
        }
        composable(Routs.SignUp.rout) {
            signUp(navController = navController)
        }
    }
}
