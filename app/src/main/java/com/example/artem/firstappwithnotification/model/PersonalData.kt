package com.example.artem.firstappwithnotification.model

import com.google.gson.annotations.SerializedName

class PersonalData(val email:String, val password:String) {
    private var mEmail = email
    private var mPassword = password
}