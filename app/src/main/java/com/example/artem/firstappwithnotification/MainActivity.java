package com.example.artem.firstappwithnotification;

import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.artem.firstappwithnotification.database.Order;
import com.example.artem.firstappwithnotification.database.Repository;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_activity_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        subscribeToFcmOrdersTopic();

        Repository.getOrdersDataBase(this).orderDao().getOrders()
                .observe(this, new Observer<List<Order>>() {
                    @Override
                    public void onChanged(@Nullable List<Order> orders) {
                        if(orders == null){
                            return;
                        }

                        mAdapter = new Adapter(MainActivity.this, orders);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
    }

    private void subscribeToFcmOrdersTopic(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!preferences.getBoolean("firstRun",false)){
            FirebaseMessaging.getInstance().subscribeToTopic("Orders");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstRun", true);
            editor.commit();
        }
    }
}
