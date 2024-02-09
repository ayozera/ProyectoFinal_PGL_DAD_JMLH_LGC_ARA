package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation

sealed class Routs(val rout : String) {
    object LogIn : Routs("logIn")
    object SignUp : Routs("signUp")
    object Profile : Routs("profile")
    object Game : Routs("game")
    object SearchBar : Routs("searchBar")
    object Match : Routs("match")
    object SelectMatch : Routs("selectMatch")
    object Score : Routs("score")
    object Invitations : Routs("invitations")
    object JukeBox : Routs("jukeBox")
    object Credits : Routs("credits")
    }
