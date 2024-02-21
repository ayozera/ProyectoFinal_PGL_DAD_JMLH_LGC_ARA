package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AppMainViewModel : ViewModel() {

    private val connection = FirebaseFirestore.getInstance()

    private var _isLogged = MutableStateFlow(false)
    val isLogged = _isLogged.asStateFlow()

    private var _playerDB = MutableStateFlow<PlayerDB?>(null)
    val playerDB = _playerDB?.asStateFlow()

    private var _player = MutableStateFlow<Player?>(null)
    val player = _player?.asStateFlow()

    private var _isMatching = MutableStateFlow(false)
    val isMatching = _isMatching.asStateFlow()


    fun logIn(uid: String?) {
        _isLogged.value = true
        //obtenemos el jugador a partir del id del usuario, realizando una consulta a firebase
        connection.collection("Player").document(uid!!).get()
            .addOnSuccessListener { document ->
                val player = document.toObject(PlayerDB::class.java)
                _playerDB = MutableStateFlow(player)
                println("Player: ${player?.name}")
                println("Player: ${player?.color}")
                _player = MutableStateFlow(
                    Player(
                        player?.name.toString(),
                        Color(android.graphics.Color.parseColor(player?.color.toString())),
                        player?.avatar.toString()
                    )
                )
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
        while (_player.value == null) {
            TimeUnit.SECONDS.sleep(1)
        }
    }


    fun logOut() {
        _isLogged.value = false
    }

    fun discardMatch() {
        _isMatching.value = false
    }

    fun startMatch() {
        _isMatching.value = true
    }
}