package com.project.newsapp.presentation.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(): ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    // If user is signed in then current user !=null
    fun isUserSignedIn() = auth.currentUser!=null

    fun createNewUser(email: String,password: String){
        auth.createUserWithEmailAndPassword(email,password)
    }
}