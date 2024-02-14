package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Match
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.SelectionMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MatchViewModel : ViewModel() {

    private var _context: Context? = null
    private var _selectionMatch: MutableStateFlow<SelectionMatch>? = null
    val selectionMatch: StateFlow<SelectionMatch>
        get() = _selectionMatch?.asStateFlow()
            ?: throw IllegalStateException("Context not initialized")

    var _gameName: MutableStateFlow<String>? = null
    val gameName: StateFlow<String>
        get() = _gameName?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _gameArt: MutableStateFlow<Int>? = null
    val gameArt: StateFlow<Int>
        get() = _gameArt?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _players: MutableStateFlow<ArrayList<Player>>? = null
    val players: StateFlow<ArrayList<Player>>
        get() = _players?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _day: MutableStateFlow<Int>? = null
    val day: StateFlow<Int>
        get() = _day?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _month: MutableStateFlow<Int>? = null
    val month: StateFlow<Int>
        get() = _month?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _year: MutableStateFlow<Int>? = null
    val year: StateFlow<Int>
        get() = _year?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    val _score: MutableStateFlow<ArrayList<Int>> = MutableStateFlow(ArrayList())
    val score = _score.asStateFlow()

    var isInitialized: Boolean = false


    fun setContext(context: Context) {
        if (isInitialized) return
        isInitialized = true
        this._context = context
        this._selectionMatch = MutableStateFlow(DataUp.loadSelection(context))
        this._gameName = MutableStateFlow(_selectionMatch!!.value.game)
        this._gameArt = MutableStateFlow(
            context.resources.getIdentifier(
                _selectionMatch!!.value.game,
                "drawable",
                context.packageName
            )
        )
        this._players = MutableStateFlow(_selectionMatch!!.value.players)
        this._day = MutableStateFlow(_selectionMatch!!.value.day)
        this._month = MutableStateFlow(_selectionMatch!!.value.month)
        this._year = MutableStateFlow(_selectionMatch!!.value.year)
        for (i in 0 until _players!!.value.size) {
            _score.value.add(0)
        }
    }

    fun getGameName(): String {
        return _gameName?.value ?: throw IllegalStateException("Context not initialized")
    }

    fun getGameArt(): Int {
        return _gameArt?.value ?: throw IllegalStateException("Context not initialized")
    }

    fun addScore(index: Int) {
        val newScoreList = _score.value.toMutableList()
        newScoreList[index] += 1
        _score.value = ArrayList(newScoreList)
    }

    fun substractScore(index: Int) {
        if (_score.value[index] > 0) {
            val newScoreList = _score.value.toMutableList()
            newScoreList[index] -= 1
            _score.value = ArrayList(newScoreList)
        }
    }

    fun saveMatch() {
        DataUp.saveMatch(
            Match(_gameName!!.value,
                _gameArt!!.value,
                _players!!.value,
                _day!!.value,
                _month!!.value,
                _year!!.value,
                _score.value
            ), _context!!
        )
    }

}