package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Comment
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Game
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.CommentDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.GameDB
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.repositories.PlayerDB
import kotlinx.coroutines.flow.MutableStateFlow
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.asStateFlow

class FirebaseViewModel : ViewModel() {
    val conexion = FirebaseFirestore.getInstance()

    private lateinit var listenerComment: ListenerRegistration

    private var _listComment = MutableStateFlow(mutableStateListOf<Comment>())
    var listComment = _listComment.asStateFlow()

    private var _game = MutableStateFlow<GameDB?>(null)

    fun listenComment() {
        listenerComment = conexion.collection("Comment").addSnapshotListener { data, error ->
            if (error == null) {
                data?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        commentDB.id = change.document.id
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value.add(comment)
                    } else if (change.type == DocumentChange.Type.MODIFIED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value[change.newIndex] = comment
                    } else {
                        val commentDB = change.document.toObject<CommentDB>()
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value.remove(comment)
                    }
                }
            }
        }
    }

    fun addComment(comment: CommentDB) {
        conexion.collection("Comment").add(comment)
    }

    fun deleteComment(idComment: String) {
        conexion.collection("Comment").document(idComment).delete()
    }

    fun removeListener() {
        listenerComment.remove()
    }

    fun loadGame(name: String) {
        //obtenemos el juego a partir de su nombre
        conexion.collection("Game").whereEqualTo("name", name).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val game = document.toObject<GameDB>()
                _game.value = game

                _listComment.value.clear()
                listenComment()
            }
        }
    }

    //obtenemos los comentarios de un juego
/*    private fun getComments(idGame: String) {
        conexion.collection("Comment").whereEqualTo("game_id", idGame).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val commentDB = document.toObject<CommentDB>()
                val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                _listComment.value.add(comment)
            }
        }
    }*/

}
