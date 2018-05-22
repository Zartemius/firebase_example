package com.example.artem.firstappwithnotification;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.artem.firstappwithnotification.database.Order;
import com.example.artem.firstappwithnotification.database.Repository;
import com.example.artem.firstappwithnotification.fcm.FirebaseRegistrationTokenService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OrdersActivity";
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    FirebaseRegistrationTokenService firebaseRegistrationTokenService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_activity_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseRegistrationTokenService = new FirebaseRegistrationTokenService();
        Log.i("TOKEN", "token " + firebaseRegistrationTokenService.getToken());


        Repository.getOrdersDataBase(this).orderDao().getOrders()
                .observe(this, new Observer<List<Order>>() {
                    @Override
                    public void onChanged(@Nullable List<Order> orders) {
                        if(orders == null){
                            return;
                        }

                        mAdapter = new Adapter(MainActivity.this, orders);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListner(new Adapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "works!", Toast.LENGTH_SHORT);

                                callActivityArticleScreen(orders.get(position).getId());
                            }
                        });
                    }
                });
    }


    private void callActivityArticleScreen(int idOfOrder){
        Intent intent = new Intent(this, OrderScreen.class);
        intent.putExtra("orderId", idOfOrder);
        startActivity(intent);
    }
}
