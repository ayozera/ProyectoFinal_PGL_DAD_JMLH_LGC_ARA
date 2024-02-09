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
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Credits
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.activities.Game
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
            LogIn(navController = navController)
        }
        composable(Routs.SignUp.rout) {
            SignUp(navController = navController)
        }
        composable(Routs.Profile.rout) {
            Profile(navController = navController)
        }
        composable(Routs.Game.rout) {
            Game(navController = navController)
        }
        composable(Routs.SearchBar.rout) {
            SearchBar(navController = navController)
        }
        composable(Routs.Match.rout) {
            Match(navController = navController)
        }
        composable(Routs.SelectMatch.rout) {
            SelectMatch(navController = navController)
        }
        composable(Routs.Score.rout) {
            Score(navController = navController)
        }
        composable(Routs.Invitations.rout) {
            Invitations(navController = navController)
        }
        composable(Routs.JukeBox.rout) {
            JukeBox(navController = navController)
        }
        composable(Routs.Credits.rout) {
            Credits(navController = navController)
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
