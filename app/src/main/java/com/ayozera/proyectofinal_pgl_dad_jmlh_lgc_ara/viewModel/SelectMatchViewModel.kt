package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.SelectionMatch
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SelectMatchViewModel : ViewModel() {

    private val connection = FirebaseFirestore.getInstance()

    private var _playersDB = MutableStateFlow(ArrayList<PlayerDB>())
    val playersDB = _playersDB.asStateFlow()

    private var _playersSelected = MutableStateFlow(ArrayList<PlayerDB>())
    val playersSelected = _playersSelected.asStateFlow()

    private var _games = MutableStateFlow(ArrayList<GameDB>())
    val games = _games.asStateFlow()

    private var _gameSelected = MutableStateFlow(GameDB())
    val gameSelected = _gameSelected.asStateFlow()

    private var _day = MutableStateFlow(0)
    val day = _day.asStateFlow()

    private var _month = MutableStateFlow(0)
    val month = _month.asStateFlow()

    private var _year = MutableStateFlow(0)
    val year = _year.asStateFlow()

    private var isInitialized = false
    private var isInitializedGame = false

    init {
        val courutineScope = CoroutineScope(Dispatchers.IO)
        courutineScope.launch {
            loadViewModel()
        }
    }
    suspend fun loadViewModel() {
        if (isInitialized) {
            return
        }
        isInitialized = true
        _playersDB = MutableStateFlow(getPlayers())
        _games = MutableStateFlow(getGames())
    }

    private suspend fun getPlayers() : ArrayList<PlayerDB> {
        return suspendCoroutine { continuation ->
            val players2 = ArrayList<PlayerDB>()
            connection.collection("Player").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val player = document.toObject<PlayerDB>()
                        player.id = document.id
                        players2.add(player)
                    }
                    continuation.resume(players2)
                }
        }
    }

    private suspend fun getGames() : ArrayList<GameDB> {
        return suspendCoroutine { continuation ->
            val games2 = ArrayList<GameDB>()
            connection.collection("Game").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val game = document.toObject<GameDB>()
                        game.id = document.id
                        games2.add(game)
                    }
                    continuation.resume(games2)
                }
        }
    }

    fun isGameSelected() : Boolean{
        return _gameSelected.value.id.isNotEmpty()
    }

    fun setGameId(game: GameDB) {
        _gameSelected.value = game
    }

    fun addPlayers(player : PlayerDB) {
        _playersSelected.value.add(player)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate(date: LocalDate) {
        _day.value = date.dayOfMonth
        _month.value = date.monthValue
        _year.value = date.year
    }

    fun saveSelections(context : Context) {
        val playersId = ArrayList<String>()
        val players = ArrayList<Player>()
        for (player in playersSelected.value) {
            playersId.add(player.id)
            players.add(Player(player.name, Color(android.graphics.Color.parseColor(player.color)), player.avatar))
        }
        DataUp.saveSelection(SelectionMatch( gameSelected.value.id, gameSelected.value.name, gameSelected.value.image, playersId, players, day.value, month.value, year.value), context)
    }

    fun clearPlayers() {
        _playersSelected.value.clear()
    }

}