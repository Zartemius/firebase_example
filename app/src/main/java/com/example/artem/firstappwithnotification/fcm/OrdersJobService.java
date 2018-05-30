package com.example.artem.firstappwithnotification.fcm;

import android.os.Bundle;
import android.util.Log;
import com.example.artem.firstappwithnotification.appdatabase.Order;
import com.example.artem.firstappwithnotification.appdatabase.OrderDao;
import com.example.artem.firstappwithnotification.appdatabase.Repository;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrdersJobService extends JobService {

    private static final String TAG = "OrdersJobService";
    private final Executor executor = Executors.newFixedThreadPool(2);
    private OrderDao orderDao = Repository.getOrdersDataBase(this).orderDao();

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "update ROOM database with last orders");
        addOrdersToSQLDataBase(job.getExtras());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private void addOrdersToSQLDataBase(Bundle bundle){
        final Order orderObj = getOrderObjectFromBundle(bundle);

        //
        Log.i("DATA FROM FCM", "Model " + orderObj.getModel()
                + " Number of order" + orderObj.getNumberOfOrder()
        + " Type Of Work " + orderObj.getTypeOfWork()
        + " Description " + orderObj.getDescription()
        + " Date " + orderObj.getDate()
        + " Contact person " + orderObj.getContactPerson()
        + " Phone " + orderObj.getPhone()
        + " Address " + orderObj.getAddress());
        //

        executor.execute(new Runnable() {
            @Override
            public void run() {
                long rec = orderDao.insertOrder(orderObj);
                Log.d(TAG,"added to db" + rec);
            }
        });
    }

    private Order getOrderObjectFromBundle(Bundle bundle){
        Order order = new Order();
        order.setNumberOfOrder(bundle.getString("numberOfOrder"));
        order.setModel(bundle.getString("model"));
        order.setTypeOfWork(bundle.getString("typeOfWork"));
        order.setDescription(bundle.getString("description"));
        order.setDate(bundle.getString("date"));
        order.setContactPerson(bundle.getString("contactPerson"));
        order.setPhone(bundle.getString("phone"));
        order.setAddress(bundle.getString("address"));

        return order;
    }
}
