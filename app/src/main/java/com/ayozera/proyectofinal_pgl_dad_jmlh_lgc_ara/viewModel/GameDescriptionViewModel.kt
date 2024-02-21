package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameDescriptionViewModel: ViewModel(){
    private var _user = MutableStateFlow("")
    val user = _user.asStateFlow()

    private var _comment = MutableStateFlow("")
    val comment = _comment.asStateFlow()

    private var _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    fun addComment(user: String, comment: String){
        _user.value = user
        _comment.value = comment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _date.value = getCurrentDateTime()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = currentDateTime.format(formatter)
        return formatted.toString()
    }
}