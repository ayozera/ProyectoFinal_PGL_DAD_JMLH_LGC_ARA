package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.SelectionMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class MatchViewModel : ViewModel()  {

    private var _context: Context? = null
    private var _selectionMatch: MutableStateFlow<SelectionMatch>? = null

    val selectionMatch: StateFlow<SelectionMatch>
        get() = _selectionMatch?.asStateFlow()
            ?: throw IllegalStateException("Context not initialized")

    // Rest of your properties...

    fun setContext(context: Context) {
        this._context = context
        this._selectionMatch = MutableStateFlow(DataUp.loadSelection(context))
        // Initialize the rest of your properties here...
    }


/*    private lateinit var context: Context
    private var _selectionMatch = MutableStateFlow(DataUp.loadSelection(context))
    val selectionMatch = _selectionMatch.asStateFlow()*/

    val _gameName = MutableStateFlow(_selectionMatch!!.value.game)
    val gameName = _gameName.asStateFlow()

    val _gameArt = MutableStateFlow(_selectionMatch!!.value.game)
    val gameArt = _gameArt.asStateFlow()

    val _players = MutableStateFlow(_selectionMatch!!.value.players)
    val players = _players.asStateFlow()

    val _day = MutableStateFlow(_selectionMatch!!.value.day)
    val day = _day.asStateFlow()

    val _month = MutableStateFlow(_selectionMatch!!.value.month)
    val month = _month.asStateFlow()

    val _year = MutableStateFlow(_selectionMatch!!.value.year)
    val year = _year.asStateFlow()

 /*   fun setContext(context: Context) {
        this.context = context
    }*/

    fun getGameName(): String {
        return _gameName.value
    }

    fun getGameArt(): Int {
        return _gameArt.value.toInt()
    }

}