package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

data class Game (
    var name: String = "",
    var description: String = "",
    var image: String = "",
    var comments: ArrayList<Comment> = ArrayList()
)