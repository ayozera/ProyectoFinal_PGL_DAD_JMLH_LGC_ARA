package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.SelectionMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class MatchViewModel : ViewModel()  {

    private var _context: Context? = null
    private var _selectionMatch: MutableStateFlow<SelectionMatch>? = null
    val selectionMatch: StateFlow<SelectionMatch>
        get() = _selectionMatch?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _gameName: MutableStateFlow<String>? = null
    val gameName : StateFlow<String>
        get() = _gameName?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _gameArt: MutableStateFlow<Int>? = null
    val gameArt : StateFlow<Int>
        get() = _gameArt?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _players: MutableStateFlow<ArrayList<Player>>? = null
    val players : StateFlow<ArrayList<Player>>
        get() = _players?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _day: MutableStateFlow<Int>? = null
    val day : StateFlow<Int>
        get() = _day?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _month: MutableStateFlow<Int>? = null
    val month : StateFlow<Int>
        get() = _month?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _year: MutableStateFlow<Int>? = null
    val year : StateFlow<Int>
        get() = _year?.asStateFlow() ?: throw IllegalStateException("Context not initialized")


    fun setContext(context: Context) {
        this._context = context
        this._selectionMatch = MutableStateFlow(DataUp.loadSelection(context))
        this._gameName = MutableStateFlow(_selectionMatch!!.value.game)
        this._gameArt = MutableStateFlow(context.resources.getIdentifier(_selectionMatch!!.value.game, "drawable", context.packageName))
        this._players = MutableStateFlow(_selectionMatch!!.value.players)
        this._day = MutableStateFlow(_selectionMatch!!.value.day)
        this._month = MutableStateFlow(_selectionMatch!!.value.month)
        this._year = MutableStateFlow(_selectionMatch!!.value.year)
    }

    fun getGameName(): String {
        return _gameName?.value ?: throw IllegalStateException("Context not initialized")
    }

    fun getGameArt(): Int {
        return _gameArt?.value ?: throw IllegalStateException("Context not initialized")
    }

}