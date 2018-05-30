package com.example.artem.firstappwithnotification.fbdatabase;

import com.example.artem.firstappwithnotification.personaldata.PersonalData;
import com.example.artem.firstappwithnotification.tokenservice.FirebaseRegistrationTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Fbdatabase {

    private PersonalData mPersonalData;
    private String id;

    public Fbdatabase(){

    }

    public Fbdatabase(PersonalData personalData){
        mPersonalData = personalData;
    }

    public void addPersonalDataToDataBase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference mDbReference = FirebaseDatabase.getInstance().getReference(); //Users
        mDbReference.child("Users").child(uid).setValue(mPersonalData);
    }

    public void addKeyToDataBase(String idOfDevice,String key){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child("Users").child(uid).child("mKeysOfDevice").child(idOfDevice).setValue(key);
    }
}
