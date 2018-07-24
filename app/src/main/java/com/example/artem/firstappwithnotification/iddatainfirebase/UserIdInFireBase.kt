package com.example.artem.firstappwithnotification.iddatainfirebase

import com.google.firebase.auth.FirebaseAuth

class UserIdInFireBase {
    companion object {
        @Synchronized
        fun getUserUid(): String {
            val user = FirebaseAuth.getInstance().currentUser
            return user!!.uid
        }
    }
}