package com.example.artem.firstappwithnotification.tokenservice;

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
    }

    public String getToken(){
        return FirebaseInstanceId.getInstance().getToken();
    }
}
