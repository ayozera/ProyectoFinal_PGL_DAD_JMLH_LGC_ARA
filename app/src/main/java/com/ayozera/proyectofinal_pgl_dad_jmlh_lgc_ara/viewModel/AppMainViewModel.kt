package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppMainViewModel : ViewModel() {

    private var _isMatching = MutableStateFlow(false)
    val isMatching = _isMatching.asStateFlow()

    fun discardMatch() {
        _isMatching.value = false
    }
}