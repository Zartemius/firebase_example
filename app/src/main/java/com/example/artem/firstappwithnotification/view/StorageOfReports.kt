package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.model.Report
import com.example.artem.firstappwithnotification.adapters.StorageOfReportsAdapter
import com.example.artem.firstappwithnotification.viewmodels.StorageOfReportsViewModel

class StorageOfReports: AppCompatActivity() {

    private lateinit var mStorageOfReportsViewModel: StorageOfReportsViewModel
    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter: StorageOfReportsAdapter
    private lateinit var listOfReports:List<Report>

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_of_reports)
        mStorageOfReportsViewModel = ViewModelProviders.of(this).get(StorageOfReportsViewModel::class.java)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        val reportsReceivedFromDB:LiveData<List<Report>> = mStorageOfReportsViewModel.getListOfReports("test")

        reportsReceivedFromDB.observe(this, Observer { reports ->
            listOfReports = reports!!
            Log.i("size_of_report", "size " + reports!!.size)
            mAdapter = StorageOfReportsAdapter(applicationContext, listOfReports)
            mRecyclerView.adapter = mAdapter
        })
    }
}