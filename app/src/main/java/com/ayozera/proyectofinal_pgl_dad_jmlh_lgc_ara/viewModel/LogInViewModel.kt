package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class LogInViewModel() : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableStateFlow(false)

/*private var _user = MutableStateFlow("")
    val user = _user.asStateFlow()

private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()*/

//    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
//        viewModelScope.launch {
//            try {
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Log.d("LogInViewModel", "Cuenta Creada con Exito")
//                            home()
//                        } else {
//                            _loading.value = false
//                            Log.d(
//                                "LogInViewModel",
//                                "Ha ocurrido un error: ${task.result.toString()}"
//                            )
//                        }
//                    }
//
//        }catch (ex: Exception){
//            Log.d("LogInViewModel", "Ha ocurrido un error: ${ex.message}")
//        }
//
//        }



//    fun signInWithUsernameAndPassword(username: String, password: String, context: Context): Boolean {
//
//        val players = DataUp.playerLoader(context) // Obtén la lista de jugadores
//        val isValidUser = players.any { it.name == username } // Verifica si el usuario es válido
//        val isValidPassword = password == "admin" // Verifica si la contraseña es correcta
//
//        return isValidUser && isValidPassword
//    }

fun signIn(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user?.isEmailVerified == true) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    onSuccess()
                } else {
                    // If email is not verified, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure - Email not verified")
                    onFailure("Email not verified.")
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                onFailure(task.exception?.message ?: "Authentication failed.")
            }
        }
}

}
