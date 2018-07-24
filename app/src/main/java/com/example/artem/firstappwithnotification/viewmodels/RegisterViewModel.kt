package com.example.artem.firstappwithnotification.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.data.FireBaseDataBase
import com.example.artem.firstappwithnotification.iddatainfirebase.UserIdInFireBase
import com.example.artem.firstappwithnotification.model.PersonalData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class RegisterViewModel(application: Application):AndroidViewModel(application) {

    private var mFireBaseAuth = FirebaseAuth.getInstance()
    private var mRegistrationWasSuccessful = false

    fun isUserInputDataCorrect(email:String, password:String):Boolean{
        if (TextUtils.isEmpty(email)) {
            showToast(R.string.enter_email)
            return false
        }

        if (TextUtils.isEmpty(password)) {
            showToast(R.string.enter_password)
            return false
        }

        if (password.length < 6) {
            showToast(R.string.password_is_too_short)
            return false
        }
        return true
    }

    fun registrationIsProcessed(email:String, password:String):Boolean{
        mFireBaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->

                    if (!task.isSuccessful) {
                        showToast(R.string.registration_was_not_successful)
                        mRegistrationWasSuccessful = false

                    } else {
                            addDataToFireBaseDataBase(email, password)
                            subscribeUserToFcm()
                            showToast(R.string.registration_was_successful)
                            mRegistrationWasSuccessful = true
                        }
                    }

        return mRegistrationWasSuccessful
    }

    private fun addDataToFireBaseDataBase(email:String, password:String){
        val personalData = PersonalData(email, password)

        FireBaseDataBase.addPersonToDataBase(personalData)
    }

    private fun subscribeUserToFcm(){
        val uId = UserIdInFireBase.Companion.getUserUid()
        FirebaseMessaging.getInstance().subscribeToTopic(uId)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d("RETRIEVE_ORDER_FROM_DB", "get failed with retrieving  ", task.exception)
                    } else {
                        Log.i("Topic", "works")
                    }
                }
    }

    private fun showToast(resId:Int){
        Toast.makeText(getApplication(),
                resId,
                Toast.LENGTH_SHORT).show()
    }
}