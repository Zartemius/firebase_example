package com.example.artem.firstappwithnotification.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseRegistrationTokenService extends FirebaseInstanceIdService{

    private static final String TAG = "FirebaseRegTokenService";
    private static final String SEND_TOKEN_SERVICE_URL = "";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "instance id new token is " + refreshedToken);

        ///?? sendtokentoserver
    }

    public String getToken(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        return refreshedToken;
    }
}
