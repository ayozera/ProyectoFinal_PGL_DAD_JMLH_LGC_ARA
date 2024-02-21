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
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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


//    fun logIn(uid: String) {
//        _isLogged.value = true
//        //obtenemos el jugador a partir del id del usuario, realizando una consulta a firebase
//        connection.collection("Player").document(uid).get()
//            .addOnSuccessListener { document ->
//                val player = document.toObject(PlayerDB::class.java)
//                _playerDB = MutableStateFlow(player)
//                println("Player: ${player?.name}")
//                println("Player: ${player?.color}")
//                _player = MutableStateFlow(
//                    Player(
//                        player?.name.toString(),
//                        Color(android.graphics.Color.parseColor(player?.color.toString())),
//                        player?.avatar.toString()
//                    )
//                )
//            }
//            .addOnFailureListener { exception ->
//                println("Error getting documents: $exception")
//            }
//        while (_player.value == null) {
//            TimeUnit.SECONDS.sleep(1)
//        }
//    }

    suspend fun getPlayer(uid: String): Player? {
        return suspendCoroutine { continuation ->
            connection.collection("Player").document(uid).get()
                .addOnSuccessListener { document ->
                    val playerDB = document.toObject(PlayerDB::class.java)
                    val player = playerDB?.let {
                        Player(
                            it.name ?: "",
                            Color(android.graphics.Color.parseColor(it.color ?: "")),
                            it.avatar ?: ""
                        )
                    }
                    continuation.resume(player)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun logIn(uid: String) {
        _isLogged.value = true
        try {
            val player = getPlayer(uid)
            if (player != null) {
                val playerDB = PlayerDB(
                    name = player.name,
                    color = player.color.toString(), // aquí podrías convertir el color a un formato que PlayerDB espera
                    avatar = player.avatar
                )
                _playerDB.value = playerDB
                println("Player: ${player.name}")
                println("Player: ${player.color}")
                _player.value = player
            } else {
                println("Player is null")
            }
        } catch (e: Exception) {
            println("Error getting player: $e")
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