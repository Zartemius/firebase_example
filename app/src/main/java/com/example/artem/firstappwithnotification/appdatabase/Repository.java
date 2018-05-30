package com.example.artem.firstappwithnotification.appdatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import java.util.List;


public class Repository {
    private static DataBase dataBase;
    private OrderDao orderDao;
    private static final Object LOCK = new Object();

    public synchronized static DataBase getOrdersDataBase(Context context){
        if(dataBase == null){
            synchronized (LOCK){
                if(dataBase == null){
                    dataBase = Room.databaseBuilder(context,DataBase.class, " orders db").build();
                }
            }
        }
        return dataBase;
    }

    public LiveData<List<Order>> getOrders(Context context){
        if(orderDao == null){
            orderDao = Repository.getOrdersDataBase(context).orderDao();
        }

        return orderDao.getOrders();
    }
}
