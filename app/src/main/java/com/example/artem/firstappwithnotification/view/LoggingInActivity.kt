package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.viewmodels.LoggingInViewModel

class LoggingInActivity:AppCompatActivity() {

    private lateinit var mLoggingInViewModel: LoggingInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)
        mLoggingInViewModel = ViewModelProviders.of(this).get(LoggingInViewModel::class.java)
        mLoggingInViewModel.validateWhetherUserIsLoggedIn()

        val buttonGetRegistered = findViewById<Button>(R.id.activity_logging_in__button_to_register)
        buttonGetRegistered?.setOnClickListener{callRegistrationActivity()}

        val buttonGetResetPassword = findViewById<Button>(R.id.activity_logging_in__button_to_reset_password)
        buttonGetResetPassword.setOnClickListener { callResetPasswordActivity() }

        val buttonGetLoggedIn = findViewById<Button>(R.id.activity_logging_in__button_to_log_in)
        buttonGetLoggedIn?.setOnClickListener { callLoggingInActivity() }
    }

    fun callLoggingInActivity(){

        val mEmailForLogIn = findViewById<EditText>(R.id.activity_logging_in__email)
        val mPasswordForLogIn = findViewById<EditText>(R.id.activity_logging_in__password)

        val email = mEmailForLogIn.getText().toString().trim { it <= ' ' }
        val password = mPasswordForLogIn.getText().toString().trim { it <= ' ' }

        if(mLoggingInViewModel.isUserInputCorrect(email,password)){
            mLoggingInViewModel.getUserLoggedIn(email,password)
        }
    }

    fun callResetPasswordActivity(){
        mLoggingInViewModel.shiftToTheNextActivity(ResetPasswordActivity::class.java)
    }

    fun callRegistrationActivity(){
        mLoggingInViewModel.shiftToTheNextActivity(RegistrationActivity::class.java)
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}