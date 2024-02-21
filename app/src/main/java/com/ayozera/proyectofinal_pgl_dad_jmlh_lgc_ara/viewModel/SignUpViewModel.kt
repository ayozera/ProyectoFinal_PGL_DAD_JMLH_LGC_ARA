package com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R
import com.ayozera.proyectofinal_pgl_dad_jmlh_lgc_ara.R.drawable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpViewModel {
    var user = mutableStateOf("")
    var password = mutableStateOf("")
    var email = mutableStateOf("")


    var errorMessage = mutableStateOf<String?>(null)

    private val auth: FirebaseAuth = Firebase.auth
    val firestore = FirebaseFirestore.getInstance()
    var avatar = mutableStateOf("")
    var color = mutableStateOf("")

//    fun createAccount(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
//                    user?.sendEmailVerification()?.addOnCompleteListener { emailVerificationTask ->
//                        if (emailVerificationTask.isSuccessful) {
//                            Log.d(TAG, "Email verification sent")
//                            onSuccess()
//                        } else {
//                            Log.w(TAG, "Failed to send email verification", emailVerificationTask.exception)
//                            onFailure("Failed to send email verification")
//                        }
//                    }
//                } else {
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    onFailure(task.exception?.message ?: "Authentication failed.")
//                }
//            }
//    }


    fun createAccount(username: String, email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val firebaseUser = auth.currentUser

                    firebaseUser?.sendEmailVerification()?.addOnCompleteListener { emailVerificationTask ->
                        if (emailVerificationTask.isSuccessful) {
                            Log.d(TAG, "Email verification sent")
                        }
                    }

                    // Asigna el color y el avatar manualmente
                    val avatar = "avatar9"
                    val color = "#ffe91e63"

                    // Create a new user document in Firestore
                    val user = hashMapOf(
                        "name" to username,
                        "email" to email,
                        "avatar" to avatar,
                        "color" to color
                        // add any other user info you want to store
                    )

                    firebaseUser?.let {
                        firestore.collection("Player").document(it.uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "User profile created")
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error creating user profile", e)
                                onFailure("Failed to create user profile")
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    onFailure(task.exception?.message ?: "Authentication failed.")
                }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Email Verification sent
                Log.d(TAG, "Email verification sent")
            } else {
                // Handle failure
                Log.e(TAG, "Failed to send email verification", task.exception)
                throw RuntimeException("Failed to send email verification", task.exception)
            }
        }
    }
}