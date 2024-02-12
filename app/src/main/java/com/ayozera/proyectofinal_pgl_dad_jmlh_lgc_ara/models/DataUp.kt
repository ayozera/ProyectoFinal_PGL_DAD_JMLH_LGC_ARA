package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models

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
    }
}