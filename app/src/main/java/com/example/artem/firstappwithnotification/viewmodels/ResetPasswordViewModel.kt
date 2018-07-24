package com.example.artem.firstappwithnotification.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.text.TextUtils
import android.widget.Toast
import com.example.artem.firstappwithnotification.R
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModel(application: Application): AndroidViewModel(application){

    private val mFireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun executeAuthProcess(email:String){
       if(TextUtils.isEmpty(email)){
           showToast(R.string.enter_email)
           return
       }

        mFireBaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast(R.string.password_was_sent_to_your_email)

                    } else {
                        showToast(R.string.password_could_not_be_sent)
                    }
                }
    }

    fun showToast(resId:Int) {
        Toast.makeText(getApplication(),
                resId,
                Toast.LENGTH_SHORT).show();
    }
}