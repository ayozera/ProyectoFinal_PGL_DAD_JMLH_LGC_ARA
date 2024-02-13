package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Game
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.User
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel {
    fun setContext(context: Context) {
        this._context = context
    }

    private var _context: Context? = null

    private var _userName = MutableStateFlow("")
    val userName = _userName

    private var _userAvatar = MutableStateFlow("")
    val userAvatar = _userAvatar

    private var _favouriteGame = MutableStateFlow("")
    val favouriteGame = _favouriteGame

    private var _matches = MutableStateFlow(ArrayList<Game>())
    val matches = _matches

    fun loadMatches() {
    DataUp.loadMatches(_context!!)
    }

}