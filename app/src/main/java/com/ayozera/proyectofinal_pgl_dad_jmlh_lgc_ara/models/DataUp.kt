package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

import android.content.Context

class DataUp {
    companion object {
        fun getSongs(): ArrayList<Song> {
            val lista = arrayListOf(
                Song("Chop Suey", "chop-suey"),
                Song("Rise up", "rise_up"),
                Song("Respira el momento", "respira_el_momento"),
                Song("Adentro", "Multiviral"),
                Song("Jeremias17.5", "jeremias17_5"),
                Song("Maquiavelico", "maquiavelico")
            )
            return lista
        }
        fun getComments(current: Context): ArrayList<Comment> {
            val lista = arrayListOf(
                Comment("Ana", "Me encanta este juego", "2021-12-12"),
                Comment("Luis", "Es muy entretenido", "2021-12-12"),
                Comment("Juan", "No me gusta", "2021-12-12"),
                Comment("Maria", "Es muy dificil", "2021-12-12"),
                Comment("Pedro", "Es muy facil", "2021-12-12")
            )
            return lista
        }
    }
}