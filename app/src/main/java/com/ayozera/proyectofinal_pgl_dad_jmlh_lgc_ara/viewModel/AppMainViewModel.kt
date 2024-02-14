package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppMainViewModel : ViewModel() {

    private var _context: Context? = null

    private var _isLogged = MutableStateFlow(false)
    val isLogged = _isLogged.asStateFlow()

    private var _user : MutableStateFlow<Player>? = null
    val user = _user?.asStateFlow()

    private var _isMatching = MutableStateFlow(false)
    val isMatching = _isMatching.asStateFlow()

    private var isContextInitialized = false


    fun setContext(context: Context) {
        if (isContextInitialized) return
        isContextInitialized = true
        this._context = context
    }

    fun logIn(userName : String) {
        _isLogged.value = true
        val players = DataUp.playerLoader(_context!!)
        players.forEach {
            if (it.name == userName) {
                _user = MutableStateFlow(it)
            }
        }
    }

    fun logOut() {
        _isLogged.value = false
        _user = null
    }

    fun discardMatch() {
        _isMatching.value = false
    }

    fun startMatch() {
        _isMatching.value = true
    }

}