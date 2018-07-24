package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.viewmodels.RegisterViewModel


class RegistrationActivity:AppCompatActivity() {

    private lateinit var mRegisterViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mRegisterViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        val buttonGetSignedUp = findViewById<Button>(R.id.activity_registration__button)
        buttonGetSignedUp.setOnClickListener { getSignedUp() }
    }

    private fun getSignedUp(){
        val emailForRegistration = findViewById<EditText>(R.id.activity_registration__email)
        val passwordForRegistration = findViewById<EditText>(R.id.activity_registration__password)

        val email = emailForRegistration.text.toString().trim {it <= ' '}
        val password = passwordForRegistration.text.toString().trim { it <= ' '}

        processRegistration(email,password)
    }

    private fun processRegistration(email:String,password:String){
        if (mRegisterViewModel.isUserInputDataCorrect(email, password)) {
            (mRegisterViewModel.registrationIsProcessed(email, password))
        }
    }
}