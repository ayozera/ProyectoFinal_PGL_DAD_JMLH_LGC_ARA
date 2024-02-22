package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.models.DataUp
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.NavigationGraph
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.navigation.Routs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class LogInViewModel() : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableStateFlow(false)

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signIn(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        // Get the user document from Firestore
                        firestore.collection("Player").document(user.uid)
                            .get()
                            .addOnSuccessListener { document: DocumentSnapshot ->
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                            }
                            .addOnFailureListener { exception: Exception ->
                                Log.d(TAG, "get failed with ", exception)
                            }

                        onSuccess(user.uid)
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
