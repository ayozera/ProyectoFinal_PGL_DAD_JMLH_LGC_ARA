package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.os.Build
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

class GameDescriptionViewModel: ViewModel(){

    val conexion = FirebaseFirestore.getInstance()

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


    fun loadGame(name: String) {
        //obtenemos el juego a partir de su nombre
        conexion.collection("Game").whereEqualTo("name", name).get().addOnSuccessListener { documents ->
            for (document in documents) {
                val game = document.toObject<GameDB>()
                _game.value = game

                _listCommentDB.value.clear()
                listenComment()
            }
        }
    }

    fun listenComment() {
        listenerComment = conexion.collection("Comment").addSnapshotListener { data, error ->
            if (error == null) {
                data?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        commentDB.id = change.document.id
                        _listCommentDB.value.add(commentDB)
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value.add(comment)
                    } else if (change.type == DocumentChange.Type.MODIFIED) {
                        val commentDB = change.document.toObject<CommentDB>()
                        _listCommentDB.value[change.newIndex] = commentDB
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value[change.newIndex] = comment
                    } else {
                        val commentDB = change.document.toObject<CommentDB>()
                        _listCommentDB.value.remove(commentDB)
                        val player = conexion.collection("Player").document(commentDB.playerId).get().result?.toObject<PlayerDB>()
                        val comment = Comment(player!!.name, commentDB.text, commentDB.date)
                        _listComment.value.remove(comment)
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
        val idComment = _listCommentDB.value.get(index).id
        return idComment.equals(_player.value!!.id)
    }
    fun deleteComment(index: Int) {
        val idComment = _listCommentDB.value.get(index).id
        conexion.collection("Comment").document(idComment).delete()
    }

    fun addComment(text: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _date.value = getCurrentDateTime()
        }
        //generamos el id del comentario a partir del generador automático de Firebase
        val id = conexion.collection("Comment").document().id
        val commentDB = CommentDB(id, _game.value!!.id, _player.value!!.id, text, _date.value)
        conexion.collection("Comment").add(commentDB)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = currentDateTime.format(formatter)
        return formatted.toString()
    }
}