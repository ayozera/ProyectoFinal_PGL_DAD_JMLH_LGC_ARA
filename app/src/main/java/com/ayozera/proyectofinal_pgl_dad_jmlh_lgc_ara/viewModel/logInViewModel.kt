package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class logInViewModel : ViewModel(){

private var _user = MutableStateFlow("")
    val user = _user.asStateFlow()

private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()




}