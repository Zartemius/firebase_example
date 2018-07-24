package com.example.artem.firstappwithnotification.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.artem.firstappwithnotification.data.FireBaseDataBase
import com.example.artem.firstappwithnotification.model.Report

class StorageOfReportsViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var mListOfReports: MutableLiveData<List<Report>>

    fun getListOfReports(id:String):LiveData<List<Report>>{
        if(mListOfReports == null) {
            mListOfReports = MutableLiveData()
            FireBaseDataBase.loadReports(id, mListOfReports)
        }
        return mListOfReports
    }
}