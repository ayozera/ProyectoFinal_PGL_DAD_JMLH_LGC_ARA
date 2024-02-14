package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

data class Match (
    val game: String,
    val gameArt: Int,
    val players: ArrayList<Player>,
    val day: Int,
    val month: Int,
    val year: Int,
    val score: ArrayList<Int>
)