package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.CommentDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameDescriptionViewModel : ViewModel() {

    private val connection = FirebaseFirestore.getInstance()

    private lateinit var listenerComment: ListenerRegistration

    private var _listCommentDB = MutableStateFlow(mutableStateListOf<CommentDB>())
    var listCommentDB = _listCommentDB.asStateFlow()

    private var _listComment = MutableStateFlow(mutableStateListOf<Comment>())
    var listComment = _listComment.asStateFlow()

    private var _game = MutableStateFlow<GameDB?>(null)
    var game = _game.asStateFlow()

    private var _player = MutableStateFlow<PlayerDB?>(null)
    val player = _player.asStateFlow()

    private var _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private var _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private var isRunning = false


    fun loadViewModel(gameName: String, player: PlayerDB) {
        //obtenemos el juego a partir de su nombre
        if (isRunning) {
            return
        }
        isRunning = true
        connection.collection("Game").whereEqualTo("name", gameName).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var game = document.toObject<GameDB>()
                    game.id = document.id
                    _game = MutableStateFlow(game)
                    _description.value = game.description

                    _listCommentDB.value.clear()
                    listenComment(game.id)
                }
            }

        _player = MutableStateFlow(player)
        _player.value!!.id = player.id
    }

    fun listenComment(gameId: String) {
        println("listenComment: $gameId")
        listenerComment = connection.collection("Comment").addSnapshotListener { data, error ->
            if (error == null) {
                data?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        commentDB.id = change.document.id
                        if (commentDB.game.equals(gameId)) {
                            _listCommentDB.value.add(commentDB)
                            if (!commentDB.player.isNullOrEmpty()) {
                                val playerDocument =
                                    connection.collection("Player").document(commentDB.player)
                                playerDocument.get().addOnSuccessListener { documentSnapshot ->
                                    if (documentSnapshot.exists()) {
                                        val player = documentSnapshot.toObject(PlayerDB::class.java)
                                        val playerName = player!!.name
                                        val comment =
                                            Comment(playerName, commentDB.text, commentDB.date)
                                        _listComment.value.add(comment)
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.d(TAG, "get failed with ", exception)
                                }
                            }
                        }
                    } else if (change.type == DocumentChange.Type.MODIFIED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        if (commentDB.game.equals(gameId)) {
                            _listCommentDB.value[change.newIndex] = commentDB
                            if (!commentDB.player.isNullOrEmpty()) {
                                val playerDocument =
                                    connection.collection("Player").document(commentDB.player)
                                playerDocument.get().addOnSuccessListener { documentSnapshot ->
                                    if (documentSnapshot.exists()) {
                                        val player = documentSnapshot.toObject(PlayerDB::class.java)
                                        val playerName = player?.name
                                        val comment =
                                            Comment(playerName!!, commentDB.text, commentDB.date)
                                        _listComment.value[change.newIndex] = comment
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.d(TAG, "get failed with ", exception)
                                }
                            }
                        }
                    } else {
                        val commentDB = change.document.toObject<CommentDB>()
                        if (commentDB.game.equals(gameId)) {
                            _listCommentDB.value.remove(commentDB)
                            if (!commentDB.player.isNullOrEmpty()) {
                                val playerDocument =
                                    connection.collection("Player").document(commentDB.player)
                                playerDocument.get().addOnSuccessListener { documentSnapshot ->
                                    if (documentSnapshot.exists()) {
                                        val player = documentSnapshot.toObject(PlayerDB::class.java)
                                        val playerName = player?.name
                                        val comment =
                                            Comment(playerName!!, commentDB.text, commentDB.date)
                                        _listComment.value.remove(comment)
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                }.addOnFailureListener { exception ->
                                    Log.d(TAG, "get failed with ", exception)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        removeListener()
    }

    fun removeListener() {
        listenerComment.remove()
    }

    fun isDeletable(index: Int): Boolean {
        //comprobamos si el comentario ha sido escrito por el usuario actual antes de poder borrarlo
        val idPlayer = _listCommentDB.value[index].player
        println("idComment: $idPlayer")
        println("player: ${_player.value!!.id}")
        println("index $index")
        return idPlayer.equals(_player.value!!.id)
    }

    fun deleteComment(index: Int) {
        val idComment = _listCommentDB.value.get(index).id
        connection.collection("Comment").document(idComment).delete()
    }

    fun addComment(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _date.value = getCurrentDateTime()
        }
        //generamos el id del comentario a partir del generador automático de Firebase
        val id = connection.collection("Comment").document().id
        val comment = hashMapOf(
            "game" to _game.value!!.id,
            "player" to _player.value!!.id,
            "text" to text,
            "date" to _date.value
        )
        connection.collection("Comment").document(id).set(comment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = currentDateTime.format(formatter)
        return formatted.toString()
    }
}