package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.model.Order
import com.example.artem.firstappwithnotification.viewmodels.OrderScreenViewModel

class OrderScreenFragment:android.support.v4.app.Fragment(){

    private lateinit var mOrderScreenViewModel: OrderScreenViewModel
    private lateinit var mUserId:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.order_description_fragment,container,false)
        mOrderScreenViewModel = ViewModelProviders.of(activity!!).get(OrderScreenViewModel::class.java)

        val data:LiveData<List<Order>> = mOrderScreenViewModel.getListOfOrders()

        val buttonCallActivityForReportCreation:Button = view.findViewById<Button>(R.id.order_description_fragment__button_create_report)
        buttonCallActivityForReportCreation.setOnClickListener {
            shiftToTheNextActivity()
        }

        data.observe(activity!!, Observer { orders ->
            var order: Order

            if(orders!!.isNotEmpty()){
                order = orders[0]
                mUserId = order.userId!!
                setOrderInfoInViews(order)
                Log.i("PUSH_NOTIFY_MESSAGE", "Message data " + order.orderId)
            }
        })
        return view
    }

    private fun shiftToTheNextActivity(){
        val intent = Intent(activity, ReportCreation::class.java)
        intent.putExtra("orderId",mUserId)
        startActivity(intent)
    }

    private fun setOrderInfoInViews(order: Order){
        var userId:TextView = view!!.findViewById(R.id.order_description_fragment__user_id)
        userId.text = order.orderId
    }
}