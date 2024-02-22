package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.SelectionMatch
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MatchViewModel : ViewModel() {

    private var _selectionMatch: MutableStateFlow<SelectionMatch>? = null

    var _gameName: MutableStateFlow<String>? = null
    val gameName: StateFlow<String>
        get() = _gameName?.asStateFlow() ?: throw IllegalStateException("Context not initialized")

    var _gameArt: MutableStateFlow<String>? = null
    val gameArt: StateFlow<String>
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

    private var playersId = ArrayList<String>()
    private var gameID = ""

    var isInitialized: Boolean = false


    fun loadViewModel(context: Context) {
        if (isInitialized) return
        isInitialized = true
        val selectionMatch = DataUp.loadSelection(context)
        this.gameID = selectionMatch!!.gameID
        this._gameName = MutableStateFlow(selectionMatch.gameName)
        this._gameArt = MutableStateFlow(selectionMatch.gameArt)
        this._players = MutableStateFlow(selectionMatch.players)
        playersId = selectionMatch.playersId
        this._day = MutableStateFlow(selectionMatch.day)
        this._month = MutableStateFlow(selectionMatch.month)
        this._year = MutableStateFlow(selectionMatch.year)
        for (i in 0 until _players!!.value.size) {
            _score.value.add(0)
        }

    }

    fun getGameName(): String {
        return _gameName?.value ?: throw IllegalStateException("Context not initialized")
    }

    fun getGameArt(): String {
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

    // Save the match in the database
    fun saveMatch() {
        val connection = FirebaseFirestore.getInstance()
        val gameId = connection.collection("Game").whereEqualTo("name", _gameName!!.value).get().result!!.documents[0].id
        val match = hashMapOf(
            "game" to gameId,
            "date" to "${_day!!.value}-${_month!!.value}-${_year!!.value}"
        )
        val matchId = connection.collection("Match").add(match).result!!.id
        connection.collection("Match").document(matchId).set(match)

        for (i in 0 until _players!!.value.size) {
            val score = hashMapOf(
                "player" to playersId[i],
                "match" to matchId,
                "points" to _score.value[i]
            )
            connection.collection("Score").add(score)
        }
    }

    fun substractFiveScore(index: Int) {
        if (_score.value[index] > 9) {
            val newScoreList = _score.value.toMutableList()
            newScoreList[index] -= 10
            _score.value = ArrayList(newScoreList)
        }
    }

    fun addFiveScore(index: Int) {
        val newScoreList = _score.value.toMutableList()
        newScoreList[index] += 10
        _score.value = ArrayList(newScoreList)
    }

}