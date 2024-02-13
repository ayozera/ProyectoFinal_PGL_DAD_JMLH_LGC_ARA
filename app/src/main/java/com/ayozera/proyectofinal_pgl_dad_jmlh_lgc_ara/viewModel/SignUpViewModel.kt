package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel {
    private var _user = MutableStateFlow("")
    val user = _user.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    fun addSingUp(user: String, password: String, email: String) {
        _user.value = user
        _password.value = password
        _email.value = email
    }
}