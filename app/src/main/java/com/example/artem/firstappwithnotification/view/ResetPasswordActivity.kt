package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.viewmodels.ResetPasswordViewModel


class ResetPasswordActivity: AppCompatActivity() {

    private lateinit var mResetPasswordModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        mResetPasswordModel = ViewModelProviders.of(this).get(ResetPasswordViewModel::class.java)

        val buttonForLeavingActivity = findViewById<Button>(R.id.activity_reset_password__button_to_get_back)
        buttonForLeavingActivity.setOnClickListener { leaveActivity() }

        val buttonForSendingEmail = findViewById<Button>(R.id.activity_reset_password__button_to_reset_password)
        buttonForSendingEmail.setOnClickListener { sendEmailForChangingPassword() }
    }

    fun leaveActivity(){
        finish()
    }

    fun sendEmailForChangingPassword(){
        val userEmail = findViewById<EditText>(R.id.activity_reset_password__email_of_user)
        val mail = userEmail.text.toString().trim { it <= ' ' }
        mResetPasswordModel.executeAuthProcess(mail)
    }
}