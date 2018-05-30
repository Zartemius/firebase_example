package com.example.artem.firstappwithnotification.personaldata;

import android.content.Context;
import com.example.artem.firstappwithnotification.installationid.AndroidIDGenerator;
import com.example.artem.firstappwithnotification.tokenservice.FirebaseRegistrationTokenService;
import java.util.HashMap;
import java.util.Map;

public class PersonalData {

    private String mEmail;
    private Map<String,String> mKeysOfDevice;
    private FirebaseRegistrationTokenService firebaseRegistrationTokenService;


    public PersonalData(String email,Context context) {
        mEmail = email;
        mKeysOfDevice = new HashMap<>();
        mKeysOfDevice.put(AndroidIDGenerator.getUniqueID(context),generateTokenForUser());
    }

    public Map<String, String> getmKeysOfDevice() {
        return mKeysOfDevice;
    }

    public String getmEmail() {
        return mEmail;
    }

    private String generateTokenForUser(){
        firebaseRegistrationTokenService = new FirebaseRegistrationTokenService();
        return firebaseRegistrationTokenService.getToken();
    }
}
