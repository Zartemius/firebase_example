package com.example.artem.firstappwithnotification.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.iddatainfirebase.UserIdInFireBase
import com.example.artem.firstappwithnotification.mainscreen.MainScreen
import com.example.artem.firstappwithnotification.model.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.messaging.FirebaseMessaging

class LoggingInViewModel(application:Application):AndroidViewModel(application){

    private val mFireBaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private var orders: MutableLiveData<List<Order>>? = null

    fun isUserInputCorrect(email:String, password:String):Boolean {

        if (TextUtils.isEmpty(email)) {
            showToast(R.string.enter_email)
            return false
        }
        if (TextUtils.isEmpty(password)) {
            showToast(R.string.enter_password)
            return false
        }

        Log.i("EMAIL", "email $email")
        Log.i("PASSWORD", "password $password")

        return true
    }

    fun getUserLoggedIn(email:String,password:String){
        mFireBaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->

                    if (!task.isSuccessful()) {
                        if (password.length < 6) {
                            showToast(R.string.wrong_password)
                        } else {
                            try {
                                throw task.getException()!!

                            } catch (e: FirebaseAuthInvalidUserException) {
                                showToast(R.string.user_was_not_found)

                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                showToast(R.string.wrong_password)
                            } catch (e: Exception) {
                                showToast(R.string.mistake_of_authorization)
                            }

                        }
                    } else {
                        subscribeUserToFcm()
                        shiftToTheNextActivity(MainScreen::class.java)
                    }
                }
    }

    fun validateWhetherUserIsLoggedIn(){
        val mAuthStateListener:FirebaseAuth.AuthStateListener

        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                shiftToTheNextActivity(MainScreen::class.java)
            }
        }
        mFireBaseAuth.addAuthStateListener(mAuthStateListener)
    }

    private fun showToast(resId:Int){
        Toast.makeText(getApplication<Application>(),
                resId,
                Toast.LENGTH_SHORT).show()
    }

    private fun subscribeUserToFcm(){
        val uid = UserIdInFireBase.Companion.getUserUid()
        FirebaseMessaging.getInstance().subscribeToTopic(uid)
                .addOnCompleteListener() {task ->
                    if (!task.isSuccessful) {
                        Log.e("RETRIEVE_ORDER_FROM_DB", "get failed with retrieving  ", task.exception)
                    } else {
                        Log.i("Topic", "works")
                    }
                }

    }

    fun shiftToTheNextActivity(newActivity: Class<*>) {
        val intent = Intent(getApplication(), newActivity)
        getApplication<Application>().startActivity(intent)
    }

}