package com.example.artem.firstappwithnotification.viewmodels


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.artem.firstappwithnotification.data.FireBaseDataBase
import com.example.artem.firstappwithnotification.model.Order

class OrderScreenViewModel: ViewModel(){

    companion object {
        val orders:MutableLiveData<List<Order>> = MutableLiveData()
    }

    fun getListOfOrders(): LiveData<List<Order>>{
        FireBaseDataBase.loadOrders(orders)
        return orders
    }
}