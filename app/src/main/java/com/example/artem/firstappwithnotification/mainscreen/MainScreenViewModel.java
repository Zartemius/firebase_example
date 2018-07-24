package com.example.artem.firstappwithnotification.mainscreen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.artem.firstappwithnotification.iddatainfirebase.UserIdInFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainScreenViewModel extends AndroidViewModel {

    public MainScreenViewModel(Application application){
        super(application);
    }

    void signOut(){
        unsubscribeFromFcm();
        FirebaseAuth.getInstance().signOut();
    }

    private void unsubscribeFromFcm(){
        String uid = UserIdInFireBase.Companion.getUserUid();
        FirebaseMessaging.getInstance().unsubscribeFromTopic(uid)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Log.d("RETRIEVE_ORDER_FROM_DB", "get failed with retrieving  ", task.getException());
                        }else{
                            Log.i("Topic", "works");
                        }
                    }
                });
    }
}
