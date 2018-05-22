package com.example.artem.firstappwithnotification;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.artem.firstappwithnotification.database.DataBase;
import com.example.artem.firstappwithnotification.database.Order;
import com.example.artem.firstappwithnotification.database.Repository;

public class OrderScreen extends AppCompatActivity {

    private int receivedId;
    TextView numberOfOrder;
    TextView model;
    TextView typeOfWork;
    TextView description;
    TextView date;
    TextView contactPersone;
    TextView phone;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        receivedId = intent.getIntExtra("orderId", 1);

        numberOfOrder = findViewById(R.id.activity_order_screen__number_of_order);
        model = findViewById(R.id.activity_order_screen__model);
        typeOfWork = findViewById(R.id.activity_order_screen__type_of_work);
        description = findViewById(R.id.activity_order_screen__description);
        date = findViewById(R.id.activity_order_screen__date);
        contactPersone = findViewById(R.id.activity_order_screen__contact_person);
        phone = findViewById(R.id.activity_order_screen__contact_phone);
        address = findViewById(R.id.activity_order_screen__address);

        new GettingOrderInformation().execute();
    }

    private class GettingOrderInformation extends AsyncTask<Void,Void,Void>{
        private DataBase db = Repository.getOrdersDataBase(getApplication());
        Order order = null;

        @Override
        protected Void doInBackground(Void... voids) {
            order = db.orderDao().findById(receivedId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            numberOfOrder.setText(order.getNumberOfOrder());
            model.setText(order.getModel());
            typeOfWork.setText(order.getTypeOfWork());
            description.setText(order.getDescription());
            date.setText(order.getDate());
            contactPersone.setText(order.getContactPerson());
            phone.setText(order.getPhone());
            address.setText(order.getAddress());
        }
    }
}
