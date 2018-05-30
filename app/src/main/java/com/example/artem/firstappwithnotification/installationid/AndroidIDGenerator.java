package com.example.artem.firstappwithnotification.installationid;

import android.content.Context;
import android.provider.Settings;


public class AndroidIDGenerator {

    public synchronized static String getUniqueID(Context context){
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
