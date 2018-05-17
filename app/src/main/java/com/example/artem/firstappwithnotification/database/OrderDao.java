package com.example.artem.firstappwithnotification.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders")
    public LiveData<List<Order>> getOrders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertOrder(Order order);

}
