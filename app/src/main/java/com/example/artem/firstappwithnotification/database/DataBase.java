package com.example.artem.firstappwithnotification.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Order.class}, version = 1)
public abstract class DataBase extends RoomDatabase{

    public abstract OrderDao orderDao();
}


