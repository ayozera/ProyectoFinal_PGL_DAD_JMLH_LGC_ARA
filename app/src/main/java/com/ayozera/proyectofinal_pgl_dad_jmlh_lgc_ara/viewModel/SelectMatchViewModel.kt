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

    private var _game = MutableStateFlow("")
    val game = _game.asStateFlow()

    private var _day = MutableStateFlow(0)
    val day = _day.asStateFlow()

    private var _month = MutableStateFlow(0)
    val month = _month.asStateFlow()

    private var _year = MutableStateFlow(0)
    val year = _year.asStateFlow()


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
        _game.value = game
    }

    fun setPlayers(players: ArrayList<Player>) {
        _players.value = players
    }

    fun setDate(day: Int, month: Int, year: Int) {
        _day.value = day
        _month.value = month
        _year.value = year
    }

}