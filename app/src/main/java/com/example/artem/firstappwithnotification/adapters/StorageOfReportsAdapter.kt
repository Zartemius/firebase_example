package com.example.artem.firstappwithnotification.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.model.Report

class StorageOfReportsAdapter(context: Context, reports:List<Report>): RecyclerView.Adapter<StorageOfReportsAdapter.NewViewHolder>() {

    private val mContext:Context = context
    private val mReports:List<Report> = reports

    class NewViewHolder(viewItem:View):RecyclerView.ViewHolder(viewItem){
        var causeOfFailure:TextView
        internal var usedSpareParts:TextView
        internal var costOfRepairs:TextView
        internal var timeForRepairs: TextView
        internal var orderId: TextView

        init{
            causeOfFailure = itemView.findViewById(R.id.item_view_holder_of_reports_storage__cause_of_failure)
            usedSpareParts = itemView.findViewById(R.id.item_view_holder_of_reports_storage__used_spare_parts)
            costOfRepairs = itemView.findViewById(R.id.item_view_holder_of_reports_storage__cost_of_repairs)
            timeForRepairs = itemView.findViewById(R.id.item_view_holder_of_reports_storage__time_for_repairs)
            orderId = itemView.findViewById(R.id.item_view_holder_of_reports_storage__id_of_order)
        }
    }

    override fun onBindViewHolder(holder: StorageOfReportsAdapter.NewViewHolder, position: Int) {

        val currentItem = mReports[position]

        val causeOfFailure = currentItem.causeOfFailure
        val usedSpareParts = currentItem.usedSpareParts
        val costOdRepairs = currentItem.costOfRepairs
        val timeForRepairs = currentItem.timeForRepairs
        val orderId  = currentItem.orderId

        holder.causeOfFailure.text = causeOfFailure
        holder.usedSpareParts.text = usedSpareParts
        holder.costOfRepairs.text = costOdRepairs
        holder.timeForRepairs.text = timeForRepairs
        holder.orderId.text = orderId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageOfReportsAdapter.NewViewHolder {
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.item_view_holder_of_reports_storage,parent,false)
        return StorageOfReportsAdapter.NewViewHolder(view)
    }


    override fun getItemCount(): Int {
        return mReports.size
    }
}