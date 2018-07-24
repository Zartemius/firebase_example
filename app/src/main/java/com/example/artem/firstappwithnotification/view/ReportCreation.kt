package com.example.artem.firstappwithnotification.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.artem.firstappwithnotification.R
import com.example.artem.firstappwithnotification.model.Report
import com.example.artem.firstappwithnotification.viewmodels.ReportCreationViewModel

class ReportCreation:AppCompatActivity() {

    private lateinit var mReportCreationViewModel: ReportCreationViewModel
    private lateinit var mCauseOfFailure:EditText
    private lateinit var mUsedSpareParts:EditText
    private lateinit var mCostOfRepairs:EditText
    private lateinit var mTimeForRepairs:EditText
    private lateinit var mComments:EditText
    private lateinit var mReceivedIdOfOrder:String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_creation)
        mReportCreationViewModel = ViewModelProviders.of(this).get(ReportCreationViewModel::class.java)
        mCauseOfFailure = findViewById (R.id.activity_report_creation__cause_of_failure)
        mUsedSpareParts = findViewById(R.id.activity_report_creation__used_spare_parts)
        mCostOfRepairs = findViewById(R.id.activity_report_creation__cost_of_repairs)
        mTimeForRepairs = findViewById(R.id.activity_report_creation__time_for_repairs)
        mComments = findViewById(R.id.activity_report_creation__comments)

        setIdOfOrderFromThePreviousActivity()

        val buttonForSubmittingReport = findViewById<Button>(R.id.activity_report__button_submit__report)
        buttonForSubmittingReport.setOnClickListener { mReportCreationViewModel.submitReportInFireBase(getInformationForReportCreation()) }
    }

    private fun getInformationForReportCreation(): Report {
        val report = Report()
        report.causeOfFailure = mCauseOfFailure.text.toString().trim { it <= ' ' }
        report.usedSpareParts = mUsedSpareParts.text.toString().trim { it <= ' '}
        report.costOfRepairs = mCostOfRepairs.text.toString().trim { it <= ' '}
        report.timeForRepairs = mTimeForRepairs.text.toString().trim { it <= ' '}
        report.comments = mComments.text.toString().trim{it <= ' '}
        report.orderId = mReceivedIdOfOrder
        return report
    }

    private fun setIdOfOrderFromThePreviousActivity(){
        val intent= intent
        mReceivedIdOfOrder = intent.getStringExtra("orderId")
    }
}