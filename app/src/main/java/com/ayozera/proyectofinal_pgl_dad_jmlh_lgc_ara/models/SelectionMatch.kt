package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB

data class SelectionMatch (
    val gameID: String,
    val gameName: String,
    val gameArt: String,
    val playersId: ArrayList<String>,
    val players: ArrayList<Player>,
    val day: Int,
    val month: Int,
    val year: Int
)