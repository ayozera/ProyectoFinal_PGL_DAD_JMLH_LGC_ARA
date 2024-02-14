package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppMainViewModel : ViewModel() {

    private var _isLogged = MutableStateFlow(false)
    val isLogged = _isLogged.asStateFlow()

    private var _user = MutableStateFlow<Player?>(null)
    //private var _user = MutableStateFlow(Player("Laura Lorena", Color.Green, (R.drawable.avatar1).toString()))
    val user = _user?.asStateFlow()

    private var _isMatching = MutableStateFlow(false)
    val isMatching = _isMatching.asStateFlow()

    private var isContextInitialized = false


    fun logIn(userName : String, context: Context) {
        _isLogged.value = true
        val players = DataUp.playerLoader(context)
        players.forEach {
            if (it.name == userName) {
                _user.value = it
            }
        }
        println("Usuario: ${_user?.value?.name}")
    }

    fun logOut() {
        _isLogged.value = false
        //_user = null
    }

    fun discardMatch() {
        _isMatching.value = false
    }

    fun startMatch() {
        _isMatching.value = true
    }

}