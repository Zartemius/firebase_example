package com.example.artem.firstappwithnotification.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.text.TextUtils
import android.widget.Toast
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.data.FireBaseDataBase
import com.example.artem.firstappwithnotification.model.Report

class ReportCreationViewModel(application:Application):AndroidViewModel(application){

    fun submitReportInFireBase(report: Report){
        if(TextUtils.isEmpty(report.causeOfFailure)){
            showWarningToast(R.string.not_specified_cause_of_failure)
            return
        }

        if(TextUtils.isEmpty(report.costOfRepairs)){
            showWarningToast(R.string.not_specified__cost_of_repairs)
            return
        }

        if(TextUtils.isEmpty(report.timeForRepairs)){
            showWarningToast(R.string.not_specified__time_for_repairs)
            return
        }

        FireBaseDataBase.submitReportInDataBase(report)
    }

    private fun showWarningToast(resId:Int){
        Toast.makeText(getApplication(),
        resId,
        Toast.LENGTH_SHORT).show()
    }
}