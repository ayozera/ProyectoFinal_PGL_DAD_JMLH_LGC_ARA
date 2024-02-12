package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp.Companion.gameLoader
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp.Companion.playerLoader
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectMatchViewModel : ViewModel() {
    private lateinit var context: Context
    private var _players = MutableStateFlow(ArrayList<Player>())
    val players = _players.asStateFlow()
    var game = ""
    var day = 0
    var month = 0
    var year = 0

    fun setContext(context: Context) {
        this.context = context
    }

    fun getPlayers(): ArrayList<Player> {
        return playerLoader(context)
    }

    fun getGames(): ArrayList<String> {
        return gameLoader(context)
    }

    fun setGame(game: String) {
        this.game = game
    }

    fun setPlayers(players: ArrayList<Player>) {
        _players.value = players
    }

    fun setDate(day: Int, month: Int, year: Int) {
        this.day = day
        this.month = month
        this.year = year
    }

}