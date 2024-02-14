package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LogInViewModel(private val appMainViewModel: AppMainViewModel) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableStateFlow(false)


    /*private var _user = MutableStateFlow("")
        val user = _user.asStateFlow()

    private var _password = MutableStateFlow("")
        val password = _password.asStateFlow()*/

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("LogInViewModel", "Cuenta Creada con Exito")
                            home()
                        } else {
                            _loading.value = false
                            Log.d(
                                "LogInViewModel",
                                "Ha ocurrido un error: ${task.result.toString()}"
                            )
                        }
                    }

            } catch (ex: Exception) {
                Log.d("LogInViewModel", "Ha ocurrido un error: ${ex.message}")
            }

        }



    fun signInWithUsernameAndPassword(username: String, password: String, context: Context): Boolean {

        val players = DataUp.playerLoader(context) // Obtén la lista de jugadores
        val isValidUser = players.any { it.name == username } // Verifica si el usuario es válido
        val isValidPassword = password == "admin" // Verifica si la contraseña es correcta

        return isValidUser && isValidPassword
    }


}
