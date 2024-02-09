package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.Game
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.User
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel {

    private var _userName = MutableStateFlow("")
    val userName = _userName

    private var _profileImage = MutableStateFlow("")
    val profileImage = _profileImage

    private var _mostPlayedGame = MutableStateFlow("")
    val mostPlayedGame = _mostPlayedGame

    private var _matches = MutableStateFlow(ArrayList<Game>())
    val matches = _matches

    private var _friends = MutableStateFlow(ArrayList<User>())
    val friends = _friends


}