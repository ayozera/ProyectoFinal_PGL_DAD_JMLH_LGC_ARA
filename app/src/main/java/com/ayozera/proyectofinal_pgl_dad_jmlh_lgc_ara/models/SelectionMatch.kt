package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

data class SelectionMatch (
    val game: String,
    val players: ArrayList<Player>,
    val day: Int,
    val month: Int,
    val year: Int
)